/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.util.ArrayList;

/**
 *
 * @author Phan Sơn
 */
public abstract class NetworkDevice {
    protected String name;
    protected String macAddress;
    protected ArrayList<PhysicalPort> physicalPortList;

    public NetworkDevice(String name, String macAddress, int numOfPorts) {
        this.name = name;
        this.macAddress = macAddress;
        this.physicalPortList = new ArrayList<>(numOfPorts);
        for (int i = 0; i < numOfPorts; i++) {
            this.physicalPortList.add(new PhysicalPort());
        }
    }

    public String getName() {
        return name;
    }

    public String getMacAddress() {
        return macAddress;
    }
    
    public int getNumberOfPhysicalPort(){
        return this.physicalPortList.size();
    }
    
    public PhysicalPort getPhysicalPort(int searchNum) {
        return this.physicalPortList.get(searchNum);
    }
}
