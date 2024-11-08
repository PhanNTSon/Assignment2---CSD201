/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import utils.Graph;

/**
 *
 * @author Phan Sơn
 */
public class Router extends NetworkDevice {

    private HashMap<String, NetworkDevice> routingTable;
    private Graph networkTopology;

    public Router(String name, String macAddress, String publicIP, Graph networkGraph) {
        super(name, macAddress, publicIP);
        this.routingTable = new HashMap<>();
        this.networkTopology = networkGraph;
    }

    @Override
    public boolean addEdge(NetworkDevice otherDevice, PhysicalLine line) {
        // Check if there's already a connection to the other device
        if (this.adjList.containsKey(otherDevice)) {
            return false; // Prevent duplicate connection
        }

        // Add new connection
        this.adjList.put(otherDevice, line);
        addRouting(otherDevice.getPublicIP(), otherDevice);
        return true;
    }

    public void addRouting(String publicIP, NetworkDevice device) {
        this.routingTable.put(publicIP, device);
    }

    @Override
    public boolean removeEdge(NetworkDevice otherDevice) {
        if (!this.adjList.containsKey(otherDevice)) {
            return false;
        }

        this.adjList.remove(otherDevice);
        this.routingTable.remove(otherDevice.getPublicIP());
        return true;
    }

    @Override
    public void recieveData(DataPacket packet) {
        if (packet.getTtl() == 0) {
            DataPacket respond = new DataPacket(this.publicIP, packet.getSrcIP(), 50);
            respond.setContentData("Data time out.");
            this.forwardData(respond);
            return;
        }
        System.out.println("Hop on: " + this.name + ", " + this.publicIP);
        if (packet.getDestIP().equalsIgnoreCase(this.publicIP)) {
            DataPacket respond = new DataPacket(this.publicIP, packet.getSrcIP(), 50);
            respond.setContentData("Ping successfully. Router " + this.name + ""
                    + ", IP: " + this.publicIP);
            this.forwardData(respond);
        } else {

            this.forwardData(packet);
        }

    }

    @Override
    public void forwardData(DataPacket packet) {
        String nextDestIP = this.getNextHopIP(this.networkTopology, this.getPublicIP(), packet.getDestIP());
        packet.setTtl(packet.getTtl() - 1);

        // If nextDestIP equal to null means destIP is not exist then forward back to srcIP
        if (nextDestIP == null) {
            DataPacket respond = new DataPacket(this.publicIP, packet.getSrcIP(), 50);
            respond.setContentData("IP Address not found.");
            this.forwardData(respond);
            return;
        }

        NetworkDevice next = this.adjList.keySet().stream().filter(key -> (key.getPublicIP().compareTo(nextDestIP) == 0)).findFirst().orElse(null);
        next.recieveData(packet);

    }

    public String getNextHopIP(Graph networkGraph, String srcIP, String destIP) {
        ArrayList<NetworkDevice> shortestPath = this.DijkstraRef(srcIP, destIP, networkGraph);
        // If shortest path is null means destIP doesn't exist then return null
        if (shortestPath == null) {
            return null;
        }
        if (shortestPath.size() > 1) {
            return shortestPath.get(1).getPublicIP();
        }
        return shortestPath.get(0).getPublicIP();
    }

    /**
     * Dijkstra Algorithm but using Priority Queue.
     *
     * @param srcIP
     * @param destIP
     * @param networkGraph
     * @return
     */
    public ArrayList<NetworkDevice> DijkstraRef(String srcIP, String destIP, Graph networkGraph) {
        HashMap<NetworkDevice, Double> distance = new HashMap<>();
        /*
        Set all vertices in Graph as key and their value is Infinite, except for
        start Vertex the value is 0
         */
        networkGraph.getVertices().stream()
                .forEach(device -> {
                    if (device.getPublicIP().equalsIgnoreCase(srcIP)) {
                        distance.put(device, 0.0);
                    } else {
                        distance.put(device, Double.MAX_VALUE);
                    }
                });

        HashMap<NetworkDevice, NetworkDevice> previous = new HashMap<>();

        // Initiate Comparerator to Compare each total distance in queue
        Comparator<NetworkDevice> c = new Comparator<NetworkDevice>() {
            @Override
            public int compare(NetworkDevice o1, NetworkDevice o2) {
                return Double.compare(distance.get(o1), distance.get(o2));
            }
        };

        PriorityQueue<NetworkDevice> queue = new PriorityQueue<>(c);

        ArrayList<NetworkDevice> visitedArr = new ArrayList<>();
        queue.add(networkGraph.getNetworkDeviceByIP(srcIP));

        // Loop until queue is not empty
        while (!queue.isEmpty()) {

            NetworkDevice current = queue.poll();
            // If vertex not Visited
            if (!visitedArr.contains(current)) {
                // If at destination vertex
                if (current.getPublicIP().equalsIgnoreCase(destIP)) {
                    // Break loop
                    break;
                }

                // Get adjList of Vertex
                current.getAdjList()
                        // For each entry
                        .forEach((adjNetworkDevice, line) -> {
                            double totalDistance = distance.get(current) + line.getWeight();

                            // If current Total Distance < distance of that vertex preivous
                            if (totalDistance < distance.get(adjNetworkDevice)) {
                                distance.put(adjNetworkDevice, totalDistance);
                                previous.put(adjNetworkDevice, current);
                            }

                            // Enqueue unvisited vertex        
                            if (!visitedArr.contains(adjNetworkDevice)) {
                                queue.add(adjNetworkDevice);
                            }
                        });
                // Add current vertex to visited array
                visitedArr.add(current);

            }
        }

        // Take right path 
        ArrayList<NetworkDevice> path = this.getPath(previous, networkGraph.getNetworkDeviceByIP(srcIP), networkGraph.getNetworkDeviceByIP(destIP));
        return path;
    }

    /**
     * Get the path from start Vertex to destination Vertex base on Map.The
     * method trace back from Destination Vertex to Start Vertex and each time
     * it do so, add the linked vertex to array.
     *
     * @param previous
     * @param srcDevice
     * @param destDevice
     * @return
     */
    public ArrayList<NetworkDevice> getPath(HashMap<NetworkDevice, NetworkDevice> previous, NetworkDevice srcDevice, NetworkDevice destDevice) {

        if (destDevice == null || srcDevice == null) {
            return null;
        }

        ArrayList<NetworkDevice> path = new ArrayList<>();

        path.add(destDevice);

        // Begin loop until NetworkDevice destV is equal to NetworkDevice startV
        while (destDevice.compareTo(srcDevice) != 0) {
            NetworkDevice current = previous.get(destDevice);
            path.add(current);
            destDevice = current;
        }
        Collections.reverse(path);
        return path;
    }

    @Override
    public int compareTo(NetworkDevice o) {
        if (o instanceof Router) {
            if (o.getMacAddress().equalsIgnoreCase(this.macAddress)) {
                return 0;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        String result = "Router: " + this.name + ", MAC: " + this.macAddress + ", IP: " + this.publicIP + "\n";
        for (Map.Entry<NetworkDevice, PhysicalLine> entry : this.adjList.entrySet()) {
            String type = "";

            if (entry.getKey() instanceof Router) {
                type = "Router: ";
            } else if (entry.getKey() instanceof Laptop) {
                type = "Laptop: ";
            }

            result += "-> " + type
                    + entry.getKey().getName()
                    + ", MAC: " + entry.getKey().getMacAddress()
                    + ", IP: " + entry.getKey().getPublicIP()
                    + " [Latency: " + entry.getValue().getLatency() + "(ms)"
                    + ", Bandwidth: " + entry.getValue().getBandwith()
                    + ", Weight: " + entry.getValue().getWeight()
                    + "]\n";
        }
        return result;
    }

    @Override
    public String toStringPartly() {
        return "Router: " + this.name + ", MAC: " + this.macAddress + ", IP: " + this.publicIP + "\n";
    }

    @Override
    public String toStringSaveInFile() {
        return "Router:" + this.name + "," + this.macAddress + "," + this.publicIP;
    }

}
