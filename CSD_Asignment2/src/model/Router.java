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
import utils.InputValidator;

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
        }

        if (packet.getDestIP().equalsIgnoreCase(this.publicIP)) {
            DataPacket respond = new DataPacket(this.publicIP, packet.getSrcIP(), 50);
            respond.setContentData("Router: " + this.publicIP + " Hello");
            this.forwardData(respond);
        } else {
            this.forwardData(packet);
        }

    }

    @Override
    public void forwardData(DataPacket packet) {
        String nextDestIP = this.getNextHopIP(this.networkTopology, this.getPublicIP(), packet.getDestIP());
        packet.setTtl(packet.getTtl() - 1);
        this.adjList.entrySet().stream().filter(entry -> (entry.getKey().getPublicIP().equalsIgnoreCase(nextDestIP))).findFirst().orElse(null).getKey().recieveData(packet);
    }

    public String getNextHopIP(Graph networkGraph, String srcIP, String destIP) {
        ArrayList<NetworkDevice> shortestPath = this.DijkstraRef(srcIP, destIP, networkGraph);
        return shortestPath.get(1).getPublicIP();
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
                        distance.put(device, Double.parseDouble("0"));
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

//    public static void main(String[] args) {
//        Graph networkGraph = new Graph();
//        ArrayList<String> macAddr = new ArrayList<>();
//        ArrayList<String> ipAddr = new ArrayList<>();
//
//        Router r1 = new Router("R1", InputValidator.getMacAddress(macAddr), InputValidator.getIpAddress(ipAddr), networkGraph);
//        Router r2 = new Router("R2", InputValidator.getMacAddress(macAddr), InputValidator.getIpAddress(ipAddr), networkGraph);
//        Router r3 = new Router("R3", InputValidator.getMacAddress(macAddr), InputValidator.getIpAddress(ipAddr), networkGraph);
//
//        networkGraph.addEdge(r1, r2, 100, 10000);
//        networkGraph.addEdge(r2, r3, 100, 10000);
//
//        networkGraph.display();
//
//        DataPacket pck = new DataPacket(r1.getPublicIP(), r3.getPublicIP(), 40);
//        pck.setContentData("HEllo btich");
//        r1.recieveData(pck);
//    }
    @Override
    public String toString() {
        String result = "Router: " + this.name + ", MAC Address: " + this.macAddress + ", IP Address: " + this.publicIP + "\n";
        for (Map.Entry<NetworkDevice, PhysicalLine> entry : this.adjList.entrySet()) {
            result += "-> " + "Router: " + entry.getKey().getName()
                    + ", MAC Address: " + entry.getKey().getMacAddress()
                    + ", IP Address: " + entry.getKey().getPublicIP()
                    + " Latency: " + entry.getValue().getLatency() + "(ms)"
                    + "Bandwidth: " + entry.getValue().getBandwith()
                    + "\n";
        }
        return result;
    }

    public String toStringPartly() {
        return "Router: " + this.name + ", MAC Address: " + this.macAddress + ", IP Address: " + this.publicIP;
    }
}
