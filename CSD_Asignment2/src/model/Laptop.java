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
import utils.RandomGenerator;

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
            if (packet.getContentData().contains("Ping")) {
                System.out.println(packet.getContentData());
                this.receivedPackets.remove(packet);
            }
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

    /**
     * @author Duong
     */
    public void sendingEmails(Graph laptopGraph) {

        //get email information
        DataPacket emailPacket = new DataPacket();

        // check empty device network system
        if (adjList.isEmpty()) {
            System.out.println("No connections available.");
            return;
        }

        // Traversal all elements of device in the network map
        System.out.println("Available devices:");
        int index = 0; // initialized index element

        //convert graph to ArrayList
        // Transfer graph into Array
        ArrayList<NetworkDevice> laptopArray = laptopGraph.toArray();

        // Display list 
        for (int i = 0; i < laptopArray.size(); i++) {
            System.out.println(i + ": " + laptopArray.get(i).toString());
        }

        // Get laptop user want to login
        int choice = InputValidator.getIntegerInput("Enter Laptop want to "
                + "send: ", 0, laptopArray.size() - 1);

        Laptop target = (Laptop) laptopArray.get(choice);

        String srcIP = getPublicIP();

        String destIP = target.getPublicIP();

        String msg = "Enter content here: ";
        String content = InputValidator.getNormalString(msg, 100);

        DataPacket datapacket = new DataPacket(srcIP, destIP, 30);

        datapacket.setContentData(content);

        forwardData(datapacket);

    }

    public void pingRouter() {
        if (this.adjList.isEmpty()) {
            System.out.println("No connection to Router");
            return;
        }
        String ip = InputValidator.getNormalString("Ping IP: ", 13);
        DataPacket pingPacket = new DataPacket(this.publicIP, ip, 30);
        this.forwardData(pingPacket);
    }

// ------------------------------------------------------------------
    public void login(Graph routerGraph, Graph laptopGraph) {
        if (!this.password.isEmpty()) {
            String inputPass = InputValidator.getNormalString("Enter password: ", 50);
            if (inputPass.compareTo(this.password) == 0) {
                this.laptopInterface(routerGraph, laptopGraph);
            } else {
                System.out.println("Incorrect password");
            }
        } else {
            this.laptopInterface(routerGraph, laptopGraph);
        }

    }

    public void laptopInterface(Graph routerGraph, Graph laptopGraph) {
        boolean loop = true;
        while (loop) {
            int bound = Menu.displayLaptopInterfaceMenu(this.name);
            int choice = InputValidator.getIntegerInput("Enter choice: ", 1, bound);
            switch (choice) {
                case 1:
                    this.connectToRouter(routerGraph);
                    break;
                case 2:
                    this.Gmail(laptopGraph);
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
            if (!InputValidator.getContinueOption("Password already exist, config new ?[Y/N]: ")) {
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

        int bandwidth = InputValidator.getIntegerInput("Enter Bandwidth (Type 0 for random): ", 0, Integer.MAX_VALUE);
        int latency = InputValidator.getIntegerInput("Enter Latency (Type 0 for random): ", 0, Integer.MAX_VALUE);

        if (bandwidth == 0) {
            bandwidth = RandomGenerator.generateRandomPositiveInteger();
        }
        if (latency == 0) {
            latency = RandomGenerator.generateRandomPositiveInteger();
        }

        routerGraph.addEdge(this, router, latency, bandwidth);
    }

    @Override
    public String toString() {
        String result = "Laptop: " + this.name + ", MAC: " + this.macAddress + ", IP: " + this.publicIP + "\n";
        for (Map.Entry<NetworkDevice, PhysicalLine> entry : this.adjList.entrySet()) {
            result += "-> " + "Router: " + entry.getKey().getName()
                    + ", MAC: " + entry.getKey().getMacAddress()
                    + ", IP: " + entry.getKey().getPublicIP()
                    + " [Latency: " + entry.getValue().getLatency() + "(ms)"
                    + ", Bandwidth: " + entry.getValue().getBandwith()
                    + ", Weight: " + entry.getValue().getWeight()
                    + "]\n";
        }
        return result;
    }

    @Override
    public String toStringPartly() {
        return "Laptop: " + this.name + ", MAC: " + this.macAddress + ", IP: " + this.publicIP + "\n";
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

    public void Gmail(Graph laptopGraph) {
        while (true) {
            System.out.println("----------Gmail Interface---------");
            System.out.println("You have: " + this.receivedPackets.size() + " unread messages.");
            int bound = Menu.displayGmailInterfaceMenu();
            int choice = InputValidator.getIntegerInput("Enter choice: ", 1, bound);
            switch (choice) {
                case 1:
                    this.sendingEmails(laptopGraph);
                    break;
                case 2:
                    this.readEmails(laptopGraph);
                    break;
                default:
                    return;
            }
        }
    }

    public void readEmails(Graph laptopGraph) {
        // Check if receivePacket is empty then display and return
        if (this.receivedPackets.isEmpty()) {
            System.out.println("No new Emails.");
            return;
        }
        // Display out with in
        for (int i = 0; i < this.receivedPackets.size(); i++) {
            System.out.println(i + ": Message from: "
                    + ((Laptop) laptopGraph.getNetworkDeviceByIP(this.receivedPackets.get(i).getSrcIP())).name);
        }
        // Get packet user want to read
        int choice = InputValidator.getIntegerInput("Enter index of messeage want to "
                + "read (Type -1 to exit) ",
                -1, this.receivedPackets.size() - 1);
        if (choice == -1) {
            return;
        }
        DataPacket target = this.receivedPackets.get(choice);
        this.receivedPackets.remove(choice);

        System.out.println("-- Message: ");
        System.out.println(target.getContentData());
    }
    
    @Override
    public String toStringSaveInFile() {
        return "Laptop:" + this.name + "," + this.macAddress + "," + this.publicIP;
    }
}
