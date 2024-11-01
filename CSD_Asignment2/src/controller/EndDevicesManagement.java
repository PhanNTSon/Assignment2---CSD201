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

    public void addLaptop(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList, String subnet) {
        String name = InputValidator.getRouterName("Enter Router name: ");
        String macAddress = InputValidator.getMacAddress(macAddressList);
        String publicIP = InputValidator.getIpAddress(publicIPList, subnet);

        networkGraph.addNetworkDevice(new Laptop(name, macAddress, publicIP));
    }

    public void removeLaptop(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        ArrayList<NetworkDevice> routerList = new ArrayList<>();
        networkGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Laptop) {
                routerList.add(vertex);
            }
        });

        // Display all Laptops
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toString());
        }

        int targetInd = InputValidator.getIntegerInput("Enter index of laptop to remove: ", 0, routerList.size() - 1);
        NetworkDevice target = routerList.get(targetInd);
        networkGraph.removeNetworkDevice(target);
        macAddressList.remove(target.getMacAddress());
        publicIPList.remove(target.getPublicIP());
    }

    public void connectToRouter(Graph routerGraph, Laptop laptop) {
        // If Laptop already connect to other Router then ask if user want replace or not
        if (!laptop.getAdjList().isEmpty()){
            if (!InputValidator.getContinueOption("Laptop have already "
                    + "connected. Do you want to replace ? [Y/N]: ")){
                return;
            }
        }
        // If replace then remove edge 
        routerGraph.removeEdge(laptop, );
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

        routerGraph.addEdge(laptop, router, latency, bandwidth);
    }

    public void loginLaptop(Graph endDevicesGraph) {
        // Transfer graph into Array
        ArrayList<NetworkDevice> laptopArray = endDevicesGraph.toArray();

        // Display list 
        for (int i = 0; i < laptopArray.size(); i++) {
            System.out.println(i + laptopArray.get(i).toString());
        }

        // Get laptop user want to login
        int choice = InputValidator.getIntegerInput("Enter Laptop want to "
                + "login: ", 0, laptopArray.size() - 1);

        Laptop target = (Laptop) laptopArray.get(choice);

        target.login();
    }

}
