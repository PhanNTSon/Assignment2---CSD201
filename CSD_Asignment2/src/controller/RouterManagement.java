/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.NetworkDevice;
import model.Router;
import utils.Graph;
import utils.InputValidator;
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
        System.out.println("All routers: ");
        routerGraph.display();
    }

    public Vertex getRouterVertex(ArrayList<Vertex> routersArray) {
        // Display all routers with index 
        for (int i = 0; i < routersArray.size(); i++) {
            System.out.println(i + ": " + routersArray.get(i).toString());
        }
        int choice = InputValidator.getIntegerInput("Router: ",
                0, routersArray.size() - 1);
        return routersArray.get(choice);
    }

    public void connectPhysicLineRouters(Graph routerGraph) {
        ArrayList<Vertex> array = routerGraph.toArray();

        Vertex target1 = this.getRouterVertex(array);
        array.remove(target1);

        Vertex target2 = this.getRouterVertex(array);

        int portNumOfRouter1 = InputValidator.getIntegerInput("Enter port "
                + "number of Router 1 [0 - "
                + (target1.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                0, target1.getDevice().getNumberOfPhysicalPort() - 1);

        int portNumOfRouter2 = InputValidator.getIntegerInput("Enter port "
                + "number of Router 2 [0 - "
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

        
        Vertex selectedVertex = this.getRouterVertex(routers);
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

}
