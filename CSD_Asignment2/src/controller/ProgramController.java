/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import java.util.Map;
import model.Laptop;
import model.NetworkDevice;
import model.PhysicalLine;
import model.Router;
import utils.Graph;
import ui.Menu;
import utils.InputValidator;

/**
 *
 * @author Phan Sơn
 */
public class ProgramController {

    private Graph networkGraph;
    private ArrayList<String> publicIPList;
    private ArrayList<String> macAddressList;
    private RouterManagement routerMan;
    private EndDevicesManagement endDeviceMan;
    private String subnet = "192.168.0";

    public ProgramController() {
        this.networkGraph = new Graph();
        this.publicIPList = new ArrayList<>();
        this.macAddressList = new ArrayList<>();
        this.routerMan = new RouterManagement();
        this.endDeviceMan = new EndDevicesManagement();
    }

    public void manageRouter() {
        boolean loop = true;
        // begin loop until user exit 
        while (loop) {
            int max_bound = Menu.displayRouterManagementMenu();
            int choice = InputValidator.getIntegerInput("Enter chocie: ", 1, max_bound);
            switch (choice) {
                case 1:
                    routerMan.addRouter(networkGraph, macAddressList, publicIPList, subnet);
                    break;
                case 2:
                    routerMan.removeRouter(networkGraph, macAddressList, publicIPList);
                    break;
                case 3:
                    routerMan.connectRouter(networkGraph);
                    break;
                case 4:
                    routerMan.displayAllRouter(networkGraph);
                    break;
                default:
                    loop = false;
                    break;
            }
        }

    }

    public void manageEndDevices() {
        boolean loop = true;
        // begin loop until user exit 
        while (loop) {
            int max_bound = Menu.displayLaptopManagementMenu();
            int choice = InputValidator.getIntegerInput("Enter chocie: ", 1, max_bound);
            switch (choice) {
                case 1:
                    endDeviceMan.addLaptop(networkGraph, macAddressList, publicIPList, subnet);
                    ;
                    break;
                case 2:
                    endDeviceMan.removeLaptop(networkGraph, macAddressList, publicIPList);
                    break;
                case 3:
                    endDeviceMan.loginLaptop(networkGraph);
                    break;
                case 4:
                    endDeviceMan.displayAllLaptop(networkGraph);
                    break;
                default:
                    loop = false;
                    break;
            }
        }
    }

    public void saveDevices() {
        FileController fc = new FileController();
        ArrayList<String> list = new ArrayList<>();
        this.networkGraph.getVertices().forEach(vertex -> {
            String s = vertex.toStringSaveInFile();
            list.add(s);
        });
        fc.writeToFile(list, "networkDevices.txt");
    }

    public void saveEdges() {
        FileController fc = new FileController();
        ArrayList<String> list = new ArrayList<>();

        this.networkGraph.getVertices().forEach(vertex -> {
            String s;
            for (Map.Entry<NetworkDevice, PhysicalLine> entry : vertex.getAdjList().entrySet()) {
                s = vertex.getMacAddress() + "," + entry.getKey().getMacAddress() + "," + entry.getValue().toString();
                list.add(s);
            }
        });
        fc.writeToFile(list, "connections.txt");

    }

    public void getEntitesFromDatabase() {
        FileController fc = new FileController();
        ArrayList<String> entitiesList = fc.readInFile("networkDevices.txt");
        if (entitiesList == null) {
            return;
        }
        // For each device add to network Device
        for (String entity : entitiesList) {
            String[] attributes = entity.split(",");

            // If device is a router
            if (attributes[0].contains("Router")) {
                String rName = attributes[0].substring(7);
                String rMac = attributes[1];
                String rIp = attributes[2];

                Router newR = new Router(rName, rMac, rIp, this.networkGraph);
                this.networkGraph.addNetworkDevice(newR);

            } else {    // If device is a laptop
                String lName = attributes[0].substring(7);
                String lMac = attributes[1];
                String lIp = attributes[2];

                Laptop newL = new Laptop(lName, lMac, lIp);
                this.networkGraph.addNetworkDevice(newL);

            }
        }
    }

    public void getEdgesFromDatabase() {
        FileController fc = new FileController();
        ArrayList<String> entitiesList = fc.readInFile("connections.txt");
        if (entitiesList == null) {
            return;
        }
        // For each device add to network Device
        for (String entity : entitiesList) {
            String[] connection = entity.split(",");

            if (connection.length == 1) {
                return;
            }

            NetworkDevice device1 = this.networkGraph.getNetworkDeviceByMAC(connection[0]);

            NetworkDevice device2 = this.networkGraph.getNetworkDeviceByMAC(connection[1]);

            int latency = Integer.parseInt(connection[2]);
            int bandwidth = Integer.parseInt(connection[3]);

            this.networkGraph.addEdge(device1, device2, latency, bandwidth);
        }
    }

}
