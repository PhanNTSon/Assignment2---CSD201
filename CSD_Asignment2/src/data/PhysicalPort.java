/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author Phan Sơn
 */
public class PhysicalPort {
    private String ipAddress;
    private String macAddress;
    private String subnetMask;
    private boolean status;

    public PhysicalPort() {
    }

    public PhysicalPort(String ipAddress, String macAddress, String subnetMask, boolean status) {
        this.ipAddress = ipAddress;
        this.macAddress = macAddress;
        this.subnetMask = subnetMask;
        this.status = status;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
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

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public void setSubnetMask(String subnetMask) {
        this.subnetMask = subnetMask;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }    
    
}
