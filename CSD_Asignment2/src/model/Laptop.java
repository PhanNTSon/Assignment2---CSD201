/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class Laptop extends NetworkDevice{
    private String password;

    public Laptop(String name, String macAddress, int numOfPorts) {
        super(name, macAddress, numOfPorts);
    }
    
    
}
