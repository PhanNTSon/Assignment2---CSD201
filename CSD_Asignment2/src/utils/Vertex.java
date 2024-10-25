/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import data.NetworkDevice;
import java.util.HashMap;

/**
 *
 * @author ADMIN
 */
public class Vertex implements Comparable<Vertex> {

    private NetworkDevice device;
    // adjList showing the the connection to which Device through which port number
    private HashMap<Vertex, Integer> adjList;

    public Vertex(NetworkDevice device) {
        this.device = device;
        this.adjList = new HashMap<>(device.getNumberOfPhysicalPort());
    }

    public NetworkDevice getLabel() {
        return device;
    }

    public HashMap<Vertex, Integer> getAdjList() {
        return adjList;
    }

    public void setDevice(NetworkDevice device) {
        this.device = device;
    }

    public void addEdge(Vertex otherDevice, int portNum){
        this.adjList.put(otherDevice, portNum);
    }
    
    @Override
    public int compareTo(Vertex o) {
        return -1;
    }
}
