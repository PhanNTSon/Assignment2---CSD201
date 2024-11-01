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

/**
 *
 * @author Phan Sơn
 */
public class RouterManagement {

    public void addRouter(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList, String subnet) {
        String newName = InputValidator.getRouterName("Enter new Router name: ");
        String newMac = InputValidator.getMacAddress(macAddressList);
        String newIp = InputValidator.getIpAddress(publicIPList, subnet);

        Router newR = new Router(newName, newMac, newIp, networkGraph);
        networkGraph.addNetworkDevice(newR);
    }

    public void removeRouter(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        ArrayList<NetworkDevice> routerList = new ArrayList<>();
        networkGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add(vertex);
            }
        });

        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toString());
        }

        int targetInd = InputValidator.getIntegerInput("Enter index of Router to remove: ", 0, routerList.size() - 1);
        NetworkDevice target = routerList.get(targetInd);
        networkGraph.removeNetworkDevice(target);
        macAddressList.remove(target.getMacAddress());
        publicIPList.remove(target.getPublicIP());
    }

    public void connectRouter(Graph routerGraph) {
        ArrayList<Router> routerList = new ArrayList<>();
        routerGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add((Router)vertex);
            }
        });

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

        int bandwidth = InputValidator.getIntegerInput("Enter Bandwidth: ", 0, Integer.MAX_VALUE);
        int latency = InputValidator.getIntegerInput("Enter Latency: ", 0, Integer.MAX_VALUE);

        routerGraph.addEdge(router1, router2, latency, bandwidth);
    }

    public void displayAllRouter(Graph routerGraph) {
        routerGraph.display();
    }

}
