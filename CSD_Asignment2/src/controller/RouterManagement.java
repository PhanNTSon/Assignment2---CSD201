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

/**
 *
 * @author Phan Sơn
 */
public class RouterManagement {

    public void addRouter(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList, String subnet) {
        System.out.println("----------Add router----------");
        String newName = InputValidator.getRouterName("Enter new Router name: ");
        String newMac = InputValidator.getMacAddress(macAddressList);
        String newIp = InputValidator.getIpAddress(publicIPList, subnet);

        Router newR = new Router(newName, newMac, newIp, networkGraph);
        networkGraph.addNetworkDevice(newR);
    }

    public void removeRouter(Graph routerGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        System.out.println("----------Remove router----------");
        if (routerGraph.isEmpty()) {
            System.out.println("No routers to remove");
            return;
        }

        ArrayList<NetworkDevice> routerList = new ArrayList<>();
        routerGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add(vertex);
            }
        });

        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toString());
        }

        int targetInd = InputValidator.getIntegerInput("Enter index of Router to remove: ", 0, routerList.size() - 1);
        NetworkDevice target = routerList.get(targetInd);
        routerGraph.removeNetworkDevice(target);
        macAddressList.remove(target.getMacAddress());
        publicIPList.remove(target.getPublicIP());
    }

    public void connectRouter(Graph routerGraph) {
        System.out.println("----------Connect router----------");

        ArrayList<Router> routerList = new ArrayList<>();
        routerGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add((Router) vertex);
            }
        });

        // If list is not more than 2 routers then exit
        if (routerList.size() < 2) {
            System.out.println("Need to be more than 2 Routers to connect.");
            return;
        }

        // Display all Routers
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toStringPartly());
        }
        int targetInd = InputValidator.getIntegerInput("Enter index of Router 1: ", 0, routerList.size() - 1);
        Router router1 = routerList.get(targetInd);

        routerList.remove(router1);

        // Display remains Router 
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toStringPartly());
        }
        targetInd = InputValidator.getIntegerInput("Enter index of Router 2: ", 0, routerList.size() - 1);
        Router router2 = routerList.get(targetInd);

        int bandwidth = InputValidator.getIntegerInput("Enter Bandwidth (Type 0 for random): ", 0, Integer.MAX_VALUE);
        int latency = InputValidator.getIntegerInput("Enter Latency (Type 0 for random): ", 0, Integer.MAX_VALUE);

        if (bandwidth == 0) {
            bandwidth = RandomGenerator.generateRandomPositiveInteger();
        }
        if (latency == 0) {
            latency = RandomGenerator.generateRandomPositiveInteger();
        }
        
        routerGraph.addEdge(router1, router2, latency, bandwidth);
    }

    public void displayAllRouter(Graph routerGraph) {
        routerGraph.display();
    }

}
