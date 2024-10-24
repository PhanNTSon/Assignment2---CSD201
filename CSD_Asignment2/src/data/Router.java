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
public class Router {

    private String name;
    private String banner;
    private String password;
    private ArrayList<PhysicalPort> physicalPortList;

    public Router(String name, int max_ports) {
        this.name = name;
        this.physicalPortList = new ArrayList<>(max_ports);
        for (int i = 0; i < max_ports; i++) {
            this.physicalPortList.add(new PhysicalPort());
        }
    }

    public String getName() {
        return name;
    }

    public String getBanner() {
        return banner;
    }

    public String getPassword() {
        return password;
    }

    public PhysicalPort getPhysicalPort(int searchNum) {
        return this.physicalPortList.get(searchNum);
    }

    public int getNumberOfPhysicalPorts() {
        return this.physicalPortList.size();
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
    
    public void configRouter(){
        
    }
}
