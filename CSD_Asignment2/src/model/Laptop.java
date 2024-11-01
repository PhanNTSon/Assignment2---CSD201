/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class Laptop extends NetworkDevice {

    private String password;

    public Laptop(String name, String macAddress, String publicIP) {
        super(name, macAddress, publicIP);
    }
// ------------------------------------------------------------------
/*
    Quang + Duong + Dat worksite inside here. Responsible for Class Laptop
    */
    /**
     * 
     * @author 
     * @param otherDevice
     * @param line
     * @return 
     */
    @Override
    public boolean addEdge(NetworkDevice otherDevice, PhysicalLine line) {
    }

    @Override
    public boolean removeEdge(NetworkDevice otherDevice) {
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
