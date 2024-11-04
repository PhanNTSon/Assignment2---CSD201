/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Laptop;
import model.NetworkDevice;
import model.Router;
import utils.Graph;
import utils.InputValidator;

/**
 *
 * @author Phan Sơn
 */
public class EndDevicesManagement {

    /**
     * Return a Graph that contains only End Devices.
     *
     * @return
     */
    public Graph getLaptopGraph(Graph networkGraph) {
        Graph endDevicesGraph = new Graph();
        // Loop through networkGraph and take out Vertex have device instance of Laptop
        networkGraph.getVertices().stream()
                .forEach(device -> {
                    // If device is instance of Router then add to Temp graph
                    if (device instanceof Laptop) {
                        endDevicesGraph.addNetworkDevice(device);
                    }
                });
        return endDevicesGraph;
    }
    
    /**
     * Return a Graph that contains only Router.
     *
     * @return
     */
    public Graph getRoutersGraph(Graph networkGraph) {
        Graph rGraph = new Graph();
        networkGraph.getVertices().stream()
                .forEach(device -> {
                    // If device is instance of Router then add to Temp graph
                    if (device instanceof Router) {
                        rGraph.addNetworkDevice(device);
                    }
                });
        return rGraph;
    }
    
    public void addLaptop(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList, String subnet) {
        System.out.println("----------Add laptop----------");
        String name = InputValidator.getLaptopName("Enter Laptop name: ");
        String macAddress = InputValidator.getMacAddress(macAddressList);
        String publicIP = InputValidator.getIpAddress(publicIPList, subnet);

        networkGraph.addNetworkDevice(new Laptop(name, macAddress, publicIP));
    }

    public void removeLaptop(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        System.out.println("----------Remove laptop----------");

        if (networkGraph.isEmpty()) {
            System.out.println("No Laptop to remove");
            return;
        }
        
        ArrayList<NetworkDevice> laptopList = networkGraph.toArray();

        // Display all Laptops
        for (int i = 0; i < laptopList.size(); i++) {
            System.out.println(i + ": " + laptopList.get(i).toString());
        }

        int targetInd = InputValidator.getIntegerInput("Enter index of laptop to remove: ", 0, laptopList.size() - 1);
        NetworkDevice target = laptopList.get(targetInd);
        
        networkGraph.removeNetworkDevice(target);
        
        macAddressList.remove(target.getMacAddress());
        publicIPList.remove(target.getPublicIP());
    }

    public void displayAllLaptop(Graph networkGraph) {
        Graph endDevicesGraph = this.getLaptopGraph(networkGraph);
        endDevicesGraph.display();
    }

    public void loginLaptop(Graph networkGraph) {
        System.out.println("----------Login laptop----------");

        Graph laptopGraph = this.getLaptopGraph(networkGraph);
        Graph routerGraph = this.getRoutersGraph(networkGraph);
        
        if (laptopGraph.isEmpty()) {
            System.out.println("No Laptop available");
            return;
        }

        // Transfer graph into Array
        ArrayList<NetworkDevice> laptopArray = laptopGraph.toArray();

        // Display list 
        for (int i = 0; i < laptopArray.size(); i++) {
            System.out.println(i + ": " + laptopArray.get(i).toString());
        }

        // Get laptop user want to login
        int choice = InputValidator.getIntegerInput("Enter Laptop want to "
                + "login: ", 0, laptopArray.size() - 1);

        Laptop target = (Laptop) laptopArray.get(choice);

        target.login(routerGraph,laptopGraph);
    }

}
