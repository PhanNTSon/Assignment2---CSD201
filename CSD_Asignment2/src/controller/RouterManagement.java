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

    public void connectRouters(Graph routerGraph) {
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
                1, target1.getDevice().getNumberOfPhysicalPort() - 1);

        int portNumOfRouter2 = InputValidator.getIntegerInput("Enter port "
                + "number of Router 1 [1 - " 
                + (target2.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                1, target2.getDevice().getNumberOfPhysicalPort() - 1);

        int latency = InputValidator.getIntegerInput("Enter latency(ms): ",
                0, Integer.MAX_VALUE);

        int bandwidth = InputValidator.getIntegerInput("Enter bandwidth: ",
                0, Integer.MAX_VALUE);

        routerGraph.addEdge(target1, target2,
                portNumOfRouter1, portNumOfRouter2,
                latency, bandwidth);
    }

    public static void main(String[] args) {
        Graph newG = new Graph();
        RouterManagement rm = new RouterManagement();

        newG.addVertex(new Router("R1", RandomGenerator.generateMacAddress(), 4));
        newG.addVertex(new Router("R2", RandomGenerator.generateMacAddress(), 4));
        newG.addVertex(new Router("R3", RandomGenerator.generateMacAddress(), 4));
        newG.addVertex(new Router("R4", RandomGenerator.generateMacAddress(), 4));

        rm.displayAllRouter(newG);
        rm.connectRouters(newG);
        rm.displayAllRouter(newG);
    }
}
