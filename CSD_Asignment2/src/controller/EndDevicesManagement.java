/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import model.Laptop;
import utils.Graph;
import utils.InputValidator;
import utils.Vertex;

/**
 *
 * @author Phan Sơn
 */
public class EndDevicesManagement {

    private ArrayList<String> macAddressList;

    public EndDevicesManagement() {
        this.macAddressList = new ArrayList<>();
    }

    public void addLaptop(Graph endDevicesGraph) {
        String name = InputValidator.getRouterName("Enter Router name: ");
        String macAddress = InputValidator.getMacAddress(this.macAddressList);
        int numOfPhysicalPort = InputValidator.getIntegerInput("Enter number of ports for Router: ");

        endDevicesGraph.addVertex(new Laptop(name, macAddress, numOfPhysicalPort));
    }

    public void removeEndDevice(Graph endDevicesGraph) {
        ArrayList<Vertex> array = endDevicesGraph.toArray();
        // Display all routers with index 
        for (int i = 0; i < array.size(); i++) {
            System.out.println(i + array.get(i).toString());
        }
        int choice = InputValidator.getIntegerInput("Enter End-device want to "
                + "remove: ", 0, array.size() - 1);
        Vertex target = array.get(choice);
        endDevicesGraph.removeVertex(target.getDevice());
    }

    public void displayAllEndDevices(Graph endDevicesGraph) {
        System.out.println("All routers: ");
        endDevicesGraph.display();
    }

    public Vertex getDeviceVertex(ArrayList<Vertex> devicesArray) {
        // Display all routers with index 
        for (int i = 0; i < devicesArray.size(); i++) {
            System.out.println(i + ": " + devicesArray.get(i).toString());
        }
        int choice = InputValidator.getIntegerInput("Device: ",
                0, devicesArray.size() - 1);
        return devicesArray.get(choice);
    }

    public void connectPhysicLineDevice(Graph networkGraph) {
        ArrayList<Vertex> array = networkGraph.toArray();

        Vertex target1 = this.getDeviceVertex(array);
        array.remove(target1);

        Vertex target2 = this.getDeviceVertex(array);

        int portNumOfRouter1 = InputValidator.getIntegerInput("Enter port "
                + "number of Device 1 [0 - "
                + (target1.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                0, target1.getDevice().getNumberOfPhysicalPort() - 1);

        int portNumOfRouter2 = InputValidator.getIntegerInput("Enter port "
                + "number of Device 2 [0 - "
                + (target2.getDevice().getNumberOfPhysicalPort() - 1) + "]: ",
                0, target2.getDevice().getNumberOfPhysicalPort() - 1);

        int latency = InputValidator.getIntegerInput("Enter latency(ms): ",
                0, Integer.MAX_VALUE);

        int bandwidth = InputValidator.getIntegerInput("Enter bandwidth: ",
                0, Integer.MAX_VALUE);

        // Connect physical Line on graph
        networkGraph.addEdge(target1, target2,
                portNumOfRouter1, portNumOfRouter2,
                latency, bandwidth);

    }

    
    
    public void loginLaptop(Graph endDevicesGraph) {
        // Transfer graph into Array
        ArrayList<Vertex> laptopArray = endDevicesGraph.toArray();

        // Display list 
        for (int i = 0; i < laptopArray.size(); i++) {
            System.out.println(i + laptopArray.get(i).toString());
        }

        // Get laptop user want to login
        int choice = InputValidator.getIntegerInput("Enter Laptop want to "
                + "login: ", 0, laptopArray.size() - 1);

        Laptop target = (Laptop) laptopArray.get(choice).getDevice();

        target.login();
    }
    
    
}
