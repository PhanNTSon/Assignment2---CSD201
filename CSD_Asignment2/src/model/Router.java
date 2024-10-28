/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
import utils.Graph;

/**
 *
 * @author Phan Sơn
 */
public class Router extends NetworkDevice {

    private String banner;
    private String password;
    private HashMap<PhysicalPort, NetworkDevice> routingTable;
    private Graph storageTopology;

    public Router(String name, String macAddress, int numOfPorts) {
        super(name, macAddress, numOfPorts);
        this.routingTable = new HashMap<>(numOfPorts);
    }

    public String getBanner() {
        return banner;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        if (this.password.compareTo(password) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(NetworkDevice o) {
        if (this.macAddress.compareTo(o.getMacAddress()) == 0) {
            if (o instanceof Router) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    public void addConnection(int numOfPort, String ipAddress, String subnetMask, boolean status, NetworkDevice otherDevice) {
        PhysicalPort portTarget = this.physicalPortList.get(numOfPort);
        portTarget.setIpAddress(ipAddress);
        portTarget.setSubnetMask(subnetMask);
        portTarget.setStatus(status);
        this.routingTable.put(portTarget, otherDevice);
    }

    public void transmitDataPacket(DataPacket packet) {
        System.out.println("Router: " + this.name + " received packet destined for " + packet.getDestIP());

        // Step 1: Check TTL
        if (packet.getTtl() <= 0) {
            System.out.println("Packet dropped: TTL expired.");
            return;
        }

        // Step 2: Check if this router is the destination
        boolean destinationFound = false;
        for (PhysicalPort port : physicalPortList) {
            if (port.getIpAddress() != null && port.getIpAddress().equals(packet.getDestIP())) {
                destinationFound = true;
                break;
            }
        }

        if (destinationFound) {
            System.out.println("Packet has reached its destination: " + this.name);
            System.out.println("Packet content: " + packet.getContentData());
            return;
        }

        // Step 3: Forward packet to the next router in routing table
        NetworkDevice nextRouter = routingTable.values().stream()
                .filter(device -> device instanceof Router)
                .findFirst().orElse(null);

        if (nextRouter != null) {
            packet.setTtl(packet.getTtl() - 1); // Decrease TTL
            System.out.println("Forwarding packet from " + this.name + " to " + nextRouter.getName());
            ((Router) nextRouter).transmitDataPacket(packet);
        } else {
            System.out.println("No route found to destination. Packet dropped.");
        }
    }

}
