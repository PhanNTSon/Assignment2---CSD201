/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Phan Sơn
 */
public class Laptop extends NetworkDevice {

    private String password;
    private List<DataPacket> receivedPackets;

    public Laptop(String name, String macAddress, String publicIP) {
        super(name, macAddress, publicIP);
        this.receivedPackets = new ArrayList<>();
    }
// ------------------------------------------------------------------
/*
    Quang + Duong + Dat worksite inside here. Responsible for Class Laptop
     */
    /**
     *
     * @author @param otherDevice
     * @param line
     * @return
     */
    @Override
    public boolean addEdge(NetworkDevice otherDevice, PhysicalLine line) {
    }

    @Override
    public boolean removeEdge(NetworkDevice otherDevice) {
    }

    /**
     * @author Quang tran
     */
    public void recieveData(DataPacket packet) {
        if (packet.getDestIP().equalsIgnoreCase(this.publicIP)) {
            receivedPackets.add(packet);
            System.out.println("Received data: " + packet.getContentData());
        } else {
            packet.setTtl(packet.getTtl() - 1);
            this.forwardData(packet);
        }

    }

    /**
     * @author Quang tran
     */
    @Override
    public void forwardData(DataPacket packet) {
        ((Router)adjList.keySet().toArray()[0]).recieveData(packet);
    }

    public void sendingEmails() {
        // Create Data Packet
        // Forward Data Packet
    }

// ------------------------------------------------------------------
    public void login() {

    }

}
