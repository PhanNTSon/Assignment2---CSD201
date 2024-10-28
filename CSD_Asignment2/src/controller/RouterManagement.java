/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.DataPacket;
import model.NetworkDevice;
import model.PhysicalPort;
import model.Router;
import utils.Graph;
import utils.InputValidator;
import utils.RandomGenerator;
import utils.Vertex;

/**
 *
 * @author Phan Sơn
 */
public class RouterManagement {

    private ArrayList<String> macAddressList;

    public RouterManagement() {
        this.macAddressList = new ArrayList<>();
    }

    public ArrayList<String> getMacAddressList() {
        return this.macAddressList;
    }

    public void addRouter(Graph routerGraph) {
        String name = InputValidator.getRouterName("Enter Router name: ");
        String macAddress = InputValidator.getMacAddress(this.macAddressList);
        int numOfPhysicalPort = InputValidator.getIntegerInput("Enter number of ports for Router: ");

        routerGraph.addVertex(new Router(name, macAddress, numOfPhysicalPort));
    }

    public void removeRouter(Graph routerGraph) {
        ArrayList<Vertex> array = routerGraph.toArray();
        // Display all routers with index 
        for (int i = 0; i < array.size(); i++) {
            System.out.println(i + array.get(i).toString());
        }
        int choice = InputValidator.getIntegerInput("Enter Router want to "
                + "remove: ", 0, array.size() - 1);
        Vertex target = array.get(choice);
        routerGraph.removeVertex(target.getDevice());
    }

    public void displayAllRouter(Graph routerGraph) {
        routerGraph.display();
    }

    public void connectPhysicRouters(Graph routerGraph) {
        ArrayList<Vertex> array = routerGraph.toArray();
        // Display all routers with index 
        for (int i = 0; i < array.size(); i++) {
            System.out.println(i + ": " + array.get(i).toString());
        }
        int choice = InputValidator.getIntegerInput("Connect from Router: ",
                0, array.size() - 1);
        Vertex target1 = array.get(choice);

        array.remove(target1);

        // Display all routers with index 
        for (int i = 0; i < array.size(); i++) {
            System.out.println(i + ": " + array.get(i).toString());
        }
        choice = InputValidator.getIntegerInput("Connect to Router: ",
                0, array.size() - 1);
        Vertex target2 = array.get(choice);

        int portNumOfRouter1 = InputValidator.getIntegerInput("Enter port "
                + "number of Router 1 [1 - "
                + (target1.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                0, target1.getDevice().getNumberOfPhysicalPort() - 1);

        int portNumOfRouter2 = InputValidator.getIntegerInput("Enter port "
                + "number of Router 2 [1 - "
                + (target2.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                0, target2.getDevice().getNumberOfPhysicalPort() - 1);

        int latency = InputValidator.getIntegerInput("Enter latency(ms): ",
                0, Integer.MAX_VALUE);

        int bandwidth = InputValidator.getIntegerInput("Enter bandwidth: ",
                0, Integer.MAX_VALUE);

        // Connect physical Line on graph
        routerGraph.addEdge(target1, target2,
                portNumOfRouter1, portNumOfRouter2,
                latency, bandwidth);

    }

    public void configureInterfaceRouter(Graph routerGraph) {
        ArrayList<Vertex> routers = routerGraph.toArray();

        // Display all routers with index
        for (int i = 0; i < routers.size(); i++) {
            System.out.println(i + ": " + routers.get(i).toString());
        }

        // Select router to configure
        int routerIndex = InputValidator.getIntegerInput("Select router to configure: ",
                0, routers.size() - 1);
        Vertex selectedVertex = routers.get(routerIndex);
        Router selectedRouter = (Router) selectedVertex.getDevice();

        // Display available ports for selected router
        for (int i = 0; i < selectedRouter.getNumberOfPhysicalPort(); i++) {
            System.out.println("Port " + i + ": " + selectedRouter.getPhysicalPort(i).toString());
        }

        // Select port to configure
        int portIndex = InputValidator.getIntegerInput("Select port to configure: ",
                0, selectedRouter.getNumberOfPhysicalPort() - 1);

        // Get IP and subnet mask from user
        String ipAddress = InputValidator.getIpAddress();
        String subnetMask = InputValidator.getSubnetMask();

        // Configure IP and subnet mask
        Vertex vertexHasConnected = selectedVertex.getVertexFromPort(portIndex);
        if (vertexHasConnected == null) {
            selectedRouter.addConnection(portIndex, ipAddress, subnetMask, true, null);
        } else {
            NetworkDevice otherDevice = vertexHasConnected.getDevice();
            selectedRouter.addConnection(portIndex, ipAddress, subnetMask, true, otherDevice);
        }

        System.out.println("Configuration complete: IP " + ipAddress + " / Subnet Mask " + subnetMask
                + " on Port " + portIndex + " of Router " + selectedRouter.getName());
    }

    public static void main(String[] args) {
        Graph newG = new Graph();
        RouterManagement rm = new RouterManagement();

//        Router routerA = new Router("RouterA", "00:0a:95:9d:68:16", 3);
//        Router routerB = new Router("RouterB", "00:0a:95:9d:68:17", 3);
//        Router routerC = new Router("RouterC", "00:0a:95:9d:68:18", 3);
//        
        rm.addRouter(newG);
        rm.addRouter(newG);
        rm.addRouter(newG);
        // Tạo và kết nối các cổng của router
//        routerA.addConnection(0, "192.168.1.1", "255.255.255.0", true, routerB);
//        routerB.addConnection(0, "192.168.1.2", "255.255.255.0", true, routerA);
//        routerB.addConnection(1, "192.168.2.1", "255.255.255.0", true, routerC);
//        routerC.addConnection(0, "192.168.2.2", "255.255.255.0", true, routerB);
        rm.configureInterfaceRouter(newG);
        rm.configureInterfaceRouter(newG);

        rm.displayAllRouter(newG);

        
    }
}
