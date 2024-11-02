/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Laptop;
import model.NetworkDevice;
import utils.Graph;
import utils.InputValidator;

/**
 *
 * @author Phan Sơn
 */
public class EndDevicesManagement {

    public void addLaptop(Graph networkGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList, String subnet) {
        System.out.println("----------Add laptop----------");
        String name = InputValidator.getLaptopName("Enter Laptop name: ");
        String macAddress = InputValidator.getMacAddress(macAddressList);
        String publicIP = InputValidator.getIpAddress(publicIPList, subnet);

        networkGraph.addNetworkDevice(new Laptop(name, macAddress, publicIP));
    }

    public void removeLaptop(Graph laptopGraph, ArrayList<String> macAddressList, ArrayList<String> publicIPList) {
        System.out.println("----------Remove laptop----------");

        ArrayList<NetworkDevice> laptopList = laptopGraph.toArray();

        // Display all Laptops
        for (int i = 0; i < laptopList.size(); i++) {
            System.out.println(i + ": " + laptopList.get(i).toString());
        }

        int targetInd = InputValidator.getIntegerInput("Enter index of laptop to remove: ", 0, laptopList.size() - 1);
        NetworkDevice target = laptopList.get(targetInd);
        laptopGraph.removeNetworkDevice(target);
        macAddressList.remove(target.getMacAddress());
        publicIPList.remove(target.getPublicIP());
    }

    public void displayAllLaptop(Graph endDevicesGraph) {
        endDevicesGraph.display();
    }

    public void loginLaptop(Graph endDevicesGraph, Graph routerGraph) {
        System.out.println("----------Login laptop----------");

        if (endDevicesGraph.isEmpty()) {
            System.out.println("No Laptop available");
            return;
        }

        // Transfer graph into Array
        ArrayList<NetworkDevice> laptopArray = endDevicesGraph.toArray();

        // Display list 
        for (int i = 0; i < laptopArray.size(); i++) {
            System.out.println(i + ": " + laptopArray.get(i).toString());
        }

        // Get laptop user want to login
        int choice = InputValidator.getIntegerInput("Enter Laptop want to "
                + "login: ", 0, laptopArray.size() - 1);

        Laptop target = (Laptop) laptopArray.get(choice);

        target.login(routerGraph,endDevicesGraph);
    }

}
