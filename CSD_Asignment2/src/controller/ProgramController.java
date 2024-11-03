/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.util.ArrayList;
import utils.Graph;
import model.*;
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

    /**
     * Return a Graph that contains only Router.
     *
     * @return
     */
    public Graph getRoutersGraph() {
        Graph routersGraph = new Graph();
        // Loop through networkGraph and take out Vertex have device instance of Router
        this.networkGraph.getVertices().stream()
                .forEach(device -> {
                    // If device is instance of Router then add to Temp graph
                    if (device instanceof Router) {
                        routersGraph.addNetworkDevice(device);
                    }
                });
        return routersGraph;
    }

    /**
     * Return a Graph that contains only End Devices.
     *
     * @return
     */
    public Graph getLaptopGraph() {
        Graph endDevicesGraph = new Graph();
        // Loop through networkGraph and take out Vertex have device instance of Laptop
        this.networkGraph.getVertices().stream()
                .forEach(device -> {
                    // If device is instance of Router then add to Temp graph
                    if (device instanceof Laptop) {
                        endDevicesGraph.addNetworkDevice(device);
                    }
                });
        return endDevicesGraph;
    }

    public void manageRouter() {
        boolean loop = true;
        // begin loop until user exit 
        while (loop) {
            Graph routersGraph = this.getRoutersGraph();
            int max_bound = Menu.displayRouterManagementMenu();
            int choice = InputValidator.getIntegerInput("Enter chocie: ", 1, max_bound);
            switch (choice) {
                case 1:
                    routerMan.addRouter(networkGraph, macAddressList, publicIPList,subnet);
                    break;
                case 2:
                    routerMan.removeRouter(routersGraph, macAddressList, publicIPList);
                    break;
                case 3:
                    routerMan.connectRouter(routersGraph);
                    break;
                case 4:
                    routerMan.displayAllRouter(routersGraph);
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
            Graph laptopGraph = this.getLaptopGraph();
            Graph routerGraph = this.getRoutersGraph();
            int max_bound = Menu.displayLaptopManagementMenu();
            int choice = InputValidator.getIntegerInput("Enter chocie: ", 1, max_bound);
            switch (choice) {
                case 1:
                    endDeviceMan.addLaptop(networkGraph, macAddressList, publicIPList, subnet);;
                    break;
                case 2:
                    endDeviceMan.removeLaptop(laptopGraph, macAddressList, publicIPList);
                    break;
                case 3:
                    endDeviceMan.loginLaptop(laptopGraph, routerGraph);
                    break;
                case 4:
                    endDeviceMan.displayAllLaptop(laptopGraph);
                    break;
                default:
                    loop = false;
                    break;
            }
        }
    }

}
