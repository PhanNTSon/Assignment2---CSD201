/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import model.NetworkDevice;
import model.PhysicalLine;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Phan Sơn
 */
public class Graph {

    private Set<NetworkDevice> vertices;

    public Graph() {
        this.vertices = new HashSet<>();
    }

    public Set<NetworkDevice> getVertices() {
        return vertices;
    }

    public boolean isEmpty() {
        return this.vertices.isEmpty();
    }

    public void clear() {
        this.vertices.clear();
    }

    public ArrayList<NetworkDevice> toArray() {
        return new ArrayList<>(this.vertices);
    }

    public NetworkDevice getNetworkDeviceByIP(String searchIP) {
        return this.vertices.stream()
                .filter(vertex -> (vertex.getPublicIP().equalsIgnoreCase(searchIP)))
                .findFirst()
                .orElse(null);
    }
    public NetworkDevice getNetworkDeviceByMAC(String searchMAC) {
        return this.vertices.stream()
                .filter(vertex -> (vertex.getMacAddress().equalsIgnoreCase(searchMAC)))
                .findFirst()
                .orElse(null);
    }

    public NetworkDevice getNetworkDevice(NetworkDevice searchDevice) {
        return this.vertices.stream()
                .filter(vertex -> (vertex.compareTo(searchDevice) == 0))
                .findFirst()
                .orElse(null);
    }

    public void addNetworkDevice(NetworkDevice newNetworkDevice) {
        vertices.add(newNetworkDevice);
    }

    public void addEdge(NetworkDevice device1, NetworkDevice device2,
            int latency, int bandwidth) {

        NetworkDevice v1 = this.getNetworkDevice(device1);
        NetworkDevice v2 = this.getNetworkDevice(device2);

        if (v1 == null) {
            this.addNetworkDevice(device1);
            v1 = this.getNetworkDevice(device1);
        }

        if (v2 == null) {
            this.addNetworkDevice(device2);
            v2 = this.getNetworkDevice(device2);
        }

        v1.addEdge(v2, new PhysicalLine(bandwidth, latency));
        v2.addEdge(v1, new PhysicalLine(bandwidth, latency));

    }

    public void display() {
        this.vertices.forEach(vertex -> {
            System.out.println(vertex.toString());
        });
    }

    public NetworkDevice removeNetworkDevice(NetworkDevice device) {
        NetworkDevice targetDevice = this.getNetworkDevice(device);
        if (targetDevice == null) {
            return null;
        } else {
            this.vertices.remove(targetDevice);
            this.vertices.forEach(vertex -> vertex.getAdjList().remove(targetDevice));
            return targetDevice;
        }
    }

    public void removeEdge(NetworkDevice device1, NetworkDevice device2) {
        if (device1.getAdjList().keySet().contains(device2)) {
            device1.getAdjList().remove(device2);
        }
        if (device2.getAdjList().keySet().contains(device1)) {
            device2.getAdjList().remove(device1);
        }
    }

}
