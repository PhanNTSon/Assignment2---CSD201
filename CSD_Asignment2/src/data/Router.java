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
public class Router extends NetworkDevice{
    private String banner;
    private String password;

    public Router() {
        super(null, null, 0);
    }

    public Router(String name, String macAddress, int numOfPorts) {
        super(name, macAddress, numOfPorts);
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
    
    
}
