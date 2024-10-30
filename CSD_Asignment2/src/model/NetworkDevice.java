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
    protected String publicIP;
    protected HashMap<NetworkDevice, PhysicalLine> adjList;

    public NetworkDevice(String name, String macAddress, String publicIP) {
        this.name = name;
        this.macAddress = macAddress;
        this.publicIP = publicIP;
        this.adjList = new HashMap<>();
    }

    public HashMap<NetworkDevice, PhysicalLine> getAdjList() {
        return adjList;
    }

    public String getPublicIP() {
        return publicIP;
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

    /**
     * Connects to another device if there is no existing connection.
     *
     * @param otherDevice The device to connect to.
     * @param line The physical line attributes.
     * @return true if connection is successful, false if it already exists.
     */
    public abstract boolean addEdge(NetworkDevice otherDevice, PhysicalLine line);

    public abstract boolean removeEdge(NetworkDevice otherDevice);
    
    public abstract void recieveData(DataPacket packet);
    
    public abstract void forwardData(DataPacket packet);
    
    @Override
    public int compareTo(NetworkDevice o) {
        return 0;
    }

    @Override
    public String toString() {
        return "NetworkDevice{" + "name: " + name + ", macAddress: " + macAddress + ", publicIP: " + publicIP + '}';
    }
    
    
}
