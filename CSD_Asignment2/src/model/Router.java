/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;
import utils.Graph;

/**
 *
 * @author Phan Sơn
 */
public class Router extends NetworkDevice {

    private String banner;
    private String password;
    private HashMap<PhysicalPort, NetworkDevice> routingTable;
    private Graph storageTopology;

    public Router(String name, String macAddress, int numOfPorts) {
        super(name, macAddress, numOfPorts);
        this.routingTable = new HashMap<>(numOfPorts);
    }

    public String getBanner() {
        return banner;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password) {
        if (this.password.compareTo(password) == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(NetworkDevice o) {
        if (this.macAddress.compareTo(o.getMacAddress()) == 0) {
            if (o instanceof Router) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    
}
