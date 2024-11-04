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

    

    

    public void manageRouter() {
        boolean loop = true;
        // begin loop until user exit 
        while (loop) {
            int max_bound = Menu.displayRouterManagementMenu();
            int choice = InputValidator.getIntegerInput("Enter chocie: ", 1, max_bound);
            switch (choice) {
                case 1:
                    routerMan.addRouter(networkGraph, macAddressList, publicIPList,subnet);
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
                    endDeviceMan.addLaptop(networkGraph, macAddressList, publicIPList, subnet);;
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

}
