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
    private String subnetMask;
    private boolean status;

    public PhysicalPort() {
        this.status = false;
    }

    public PhysicalPort(String ipAddress, String subnetMask, boolean status) {
        this.ipAddress = ipAddress;
        this.subnetMask = subnetMask;
        this.status = status;
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

}
