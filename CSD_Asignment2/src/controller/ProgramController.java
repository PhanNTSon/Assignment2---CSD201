/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

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
    private RouterManagement routerMan;
    private EndDevicesManagement endDeviceMan;

    public ProgramController() {
        this.networkGraph = new Graph();
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
                .forEach(vertex -> {
                    // If vertex is instance of Router then add to Temp graph
                    if (vertex.getDevice() instanceof Router) {
                        routersGraph.addVertex(vertex);
                    }
                });
        return routersGraph;
    }

    /**
     * Return a Graph that contains only End Devices.
     *
     * @return
     */
    public Graph getEndDevicesGraph() {
        Graph endDevicesGraph = new Graph();
        // Loop through networkGraph and take out Vertex have device instance of Laptop
        this.networkGraph.getVertices().stream()
                .forEach(vertex -> {
                    // If vertex is instance of Router then add to Temp graph
                    if (vertex.getDevice() instanceof Laptop) {
                        endDevicesGraph.addVertex(vertex);
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
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:

                    break;
                default:
                    loop = false;
                    break;
            }
        }

    }

    public void manageEndDevices() {
        Graph endDevicesGraph = this.getEndDevicesGraph();
    }
}
