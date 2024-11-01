/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashMap;

/**
 *
 * @author Phan Sơn
 */
public class Laptop extends NetworkDevice {

    private String password;
    private HashMap<NetworkDevice, PhysicalLine> adjList;

    public Laptop(String name, String macAddress, String publicIP) {
        super(name, macAddress, publicIP);
        this.adjList = new HashMap<>();
    }

// ------------------------------------------------------------------
/*
    Quang + Duong + Dat worksite inside here. Responsible for Class Laptop
    */
    /**
     * 
     * @author le tien dat
     * @param otherDevice
     * @param line
     * @return 
     */
    
    @Override        
    public boolean addEdge(NetworkDevice otherDevice, PhysicalLine line) {       
        //adjlist save connect from laptop to otherdevice, checking device connected otherdevice         
        if (adjList.containsKey(otherDevice)) {
            System.out.println("Already connected to " + otherDevice.getName());           
            return false; 
        }
        //add new connect in adjlist, device conect to otherdevice through line
        adjList.put(otherDevice, line);
        System.out.println("Connected to " + otherDevice.getName());
            return true;
    }
/**
     * 
     * @author le tien dat
     * @param otherDevice
     * @param line
     * @return 
     */
    @Override
    public boolean removeEdge(NetworkDevice otherDevice) {
        //check device exist in adjlist, if can't see list connect return false
        if (!adjList.containsKey(otherDevice)) {
            return false;
        }
        //disconnect 
        adjList.remove(otherDevice);
        System.out.println("Disconnected from " + otherDevice.getName());
            return true;
    }

    @Override
    public void recieveData(DataPacket packet) {
    }

    @Override
    public void forwardData(DataPacket packet) {
    }
    public void sendingEmails(){
        // Create Data Packet
        // Forward Data Packet
    }

// ------------------------------------------------------------------

    public void login(){
        
    }
    

}
