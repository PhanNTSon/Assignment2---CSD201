/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.stream.IntStream;
import model.NetworkDevice;
import model.PhysicalLine;
import model.Router;
import utils.Graph;
import utils.InputValidator;

/**
 *
 * @author Phan Sơn
 */
public class RouterManagement {

    public void addRouter(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        String newName = InputValidator.getRouterName("Enter new Name: ");
        String newMac = InputValidator.getMacAddress(macAddressList);
        String newIp = InputValidator.getIpAddress(publicIPList);

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

    public void connectRouter(Graph networkGraph) {
        ArrayList<NetworkDevice> routerList = new ArrayList<>();
        networkGraph.getVertices().stream().forEach(vertex -> {
            if (vertex instanceof Router) {
                routerList.add(vertex);
            }
        });

        // Get Router 1
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toString());
        }
        int targetInd = InputValidator.getIntegerInput("Enter index of Router 1: ", 0, routerList.size() - 1);
        NetworkDevice router1 = routerList.get(targetInd);

        routerList.remove(router1);

        // Get Router 2
        for (int i = 0; i < routerList.size(); i++) {
            System.out.println(i + ": " + routerList.get(i).toString());
        }
        targetInd = InputValidator.getIntegerInput("Enter index of Router 2: ", 0, routerList.size() - 1);
        NetworkDevice router2 = routerList.get(targetInd);

        int bandwidth = InputValidator.getIntegerInput("Enter Bandwidth: ", 0, Integer.MAX_VALUE);
        int latency = InputValidator.getIntegerInput("Enter Latency: ", 0, Integer.MAX_VALUE);
        
        networkGraph.addEdge(router1, router2, latency, bandwidth);
    }
    
    
}
