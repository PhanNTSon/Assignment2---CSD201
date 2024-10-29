/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Phan Sơn
 */
public abstract class NetworkDevice implements Comparable<NetworkDevice> {

    protected String name;
    protected String macAddress;
    protected HashMap<NetworkDevice, ConnectionDetail> adjList;

    public NetworkDevice(String name, String macAddress, int numOfPorts) {
        this.name = name;
        this.macAddress = macAddress;
        this.adjList = new HashMap<>(numOfPorts);
    }

    public HashMap<NetworkDevice, ConnectionDetail> getAdjList() {
        return adjList;
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public int getNumberOfPhysicalPort() {
        return this.adjList.size();
    }

    public PhysicalPort getPhysicalPort(int searchNum) {
        return this.adjList.values().stream()
                .filter(connect -> (
                        connect.getPort().getNumOfPortInDevice() == searchNum))
                .findFirst().orElse(null).getPort();
    }

     /**
     * Connects to another device if there is no existing connection.
     * 
     * @param otherDevice The device to connect to.
     * @param port        The physical port used for the connection.
     * @param line        The physical line attributes.
     * @return true if connection is successful, false if it already exists.
     */
    public boolean addEdge(NetworkDevice otherDevice, PhysicalPort port, PhysicalLine line) {
        // Check if there's already a connection to the other device
        if (this.adjList.containsKey(otherDevice)) {
            return false; // Prevent duplicate connection
        }

        // Add new connection
        this.adjList.put(otherDevice, new ConnectionDetail(port, line));
        return true;
    }
    
    @Override
    public int compareTo(NetworkDevice o) {
        return 0;
    }
}
