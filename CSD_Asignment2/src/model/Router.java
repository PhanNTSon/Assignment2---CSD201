/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;
import java.util.HashMap;
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
        if (o instanceof Router) {
            return 0;
        } else {
            return -1;
        }
    }

    public void addConnection(int numOfPort, String ipAddress, String subnetMask, boolean status, NetworkDevice o) {
        PhysicalPort target = this.physicalPortList.get(numOfPort);
        target.setIpAddress(ipAddress);
        target.setSubnetMask(subnetMask);
        target.setStatus(status);
        this.routingTable.put(target, o);
    }
    
    
}
