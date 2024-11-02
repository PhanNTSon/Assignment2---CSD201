/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ui.Menu;
import utils.Graph;
import utils.InputValidator;

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
        this.password = "";
    }

    public void setPassword(String password) {
        this.password = password;
    }

// ------------------------------------------------------------------
/*
    Quang + Duong + Dat worksite inside here. Responsible for Class Laptop
     */
    /**
     *
     * @author @param otherDevice
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
        ((Router) adjList.keySet().toArray()[0]).recieveData(packet);
    }

    public void sendingEmails() {
        // Create Data Packet
        // Forward Data Packet
    }

    public void pingRouter() {
        String ip = InputValidator.getNormalString("Ping IP: ", 13);
        DataPacket pingPacket = new DataPacket(this.publicIP, ip, 30);
        this.forwardData(pingPacket);
    }

// ------------------------------------------------------------------
    public void login(Graph routerGraph) {
        if (!this.password.isEmpty()) {
            String inputPass = InputValidator.getNormalString("Enter password: ", 50);
            if (inputPass.compareTo(this.password) == 0) {
                this.laptopInterface(routerGraph);
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            this.laptopInterface(routerGraph);
        }

    }

    public void laptopInterface(Graph routerGraph) {
        boolean loop = true;
        while (loop) {
            int bound = Menu.displayLaptopInterfaceMenu();
            int choice = InputValidator.getIntegerInput("Enter choice: ", 1, bound);
            switch (choice) {
                case 1:
                    this.connectToRouter(routerGraph);
                    break;
                case 2:
                    this.sendingEmails();
                    break;
                case 3:
                    this.pingRouter();
                    break;
                case 4:
                    this.configPassword();
                    break;
                default:
                    loop = false;
                    break;
            }
        }
    }

    public void configPassword() {
        System.out.println("---------Config password---------");
        if (!this.password.isEmpty()) {
            if (!InputValidator.getContinueOption("Password already exist, config new ?")) {
                return;
            }
        }
        String newPass = InputValidator.getNormalString("Enter new password: ", 50);
        this.setPassword(newPass);
    }

    public void connectToRouter(Graph routerGraph) {
        System.out.println("---------Connect router---------");
        if (routerGraph.isEmpty()) {
            System.out.println("No Routers to connect");
            return;
        }

        // If Laptop already connect to other Router then ask if user want replace or not
        if (!this.getAdjList().isEmpty()) {
            if (!InputValidator.getContinueOption("Laptop have already "
                    + "connected. Do you want to replace ? [Y/N]: ")) {
                return;
            }
            // If replace then remove edge 
            ((Router) this.adjList.keySet().toArray()[0]).getAdjList().remove(this);
            this.adjList.clear();
        }

        // Get new Router and connect it
        ArrayList<Router> routerList = new ArrayList<>();
        routerGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add((Router) vertex);
            }
        });

        // Display all Routers
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toStringPartly());
        }
        int targetInd = InputValidator.getIntegerInput("Enter index of Router: ", 0, routerList.size() - 1);
        Router router = routerList.get(targetInd);

        int bandwidth = InputValidator.getIntegerInput("Enter Bandwidth: ", 0, Integer.MAX_VALUE);
        int latency = InputValidator.getIntegerInput("Enter Latency: ", 0, Integer.MAX_VALUE);

        routerGraph.addEdge(this, router, latency, bandwidth);
    }

    @Override
    public String toString() {
        String result = "Laptop: " + this.name + ", MAC Address: " + this.macAddress + ", IP Address: " + this.publicIP + "\n";
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
        return "Laptop: " + this.name + ", MAC Address: " + this.macAddress + ", IP Address: " + this.publicIP;
    }

    @Override
    public int compareTo(NetworkDevice o) {
        if (o instanceof Laptop) {
            if (o.getMacAddress().equalsIgnoreCase(this.macAddress)) {
                return 0;
            }
        }
        return -1;
    }

}
