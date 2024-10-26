/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import data.NetworkDevice;
import data.PhysicalLine;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ADMIN
 */
public class Vertex implements Comparable<Vertex> {

    private NetworkDevice device;
    // adjList showing the the connection to which Device through which port number
    private HashMap<Vertex, PhysicalLine> adjList;

    public Vertex(NetworkDevice device) {
        this.device = device;
        this.adjList = new HashMap<>(device.getNumberOfPhysicalPort());
    }

    public NetworkDevice getDevice() {
        return device;
    }

    public HashMap<Vertex, PhysicalLine> getAdjList() {
        return adjList;
    }

    public void setDevice(NetworkDevice device) {
        this.device = device;
    }

    public void addEdge(Vertex otherDevice, int portNum, int latency, int bandwith) {
        this.adjList.put(otherDevice, new PhysicalLine(portNum, bandwith, latency));
    }

    @Override
    public int compareTo(Vertex o) {
        return this.device.compareTo(o.getDevice());
    }

    @Override
    public String toString() {
        String result = "-------------------\n";
        result += "Name: " + this.getDevice().getName() + ".\n";
        result += "Mac address: " + this.getDevice().getMacAddress() + ".\n";
        result += "Number of physical ports: " + this.getDevice().getNumberOfPhysicalPort() + ".\n";
        for (Map.Entry<Vertex, PhysicalLine> entry : this.adjList.entrySet()) {
            result += "Port [" + entry.getValue().getNumOfPortFrom() + "] " + entry.getKey().getDevice().getName() + "\n";
        }
        return result;
    }
}
