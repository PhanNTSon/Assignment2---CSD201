/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class PhysicalPort {

    private int numOfPortInDevice;
    private String ipAddress;
    private String subnetMask;
    private boolean status;

    public PhysicalPort(int numOfPortInDevice) {
        this.numOfPortInDevice = numOfPortInDevice;
        this.status = true;
    }

    
    public PhysicalPort(int numOfPortInDevice, String ipAddress, String subnetMask) {
        this.numOfPortInDevice = numOfPortInDevice;
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
        this.status = true;
    }

    public int getNumOfPortInDevice() {
        return numOfPortInDevice;
    }

    public void setNumOfPortInDevice(int numOfPortInDevice) {
        this.numOfPortInDevice = numOfPortInDevice;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getSubnetMask() {
        return subnetMask;
    }

    public boolean isStatus() {
        return status;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "["+this.ipAddress + " | " + this.subnetMask + " | " + this.status + "]";
    }
}
