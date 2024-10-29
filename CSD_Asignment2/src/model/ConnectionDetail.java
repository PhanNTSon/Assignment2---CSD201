/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class ConnectionDetail {
    private PhysicalPort port;
    private PhysicalLine line;

    public ConnectionDetail(PhysicalPort port, PhysicalLine line) {
        this.port = port;
        this.line = line;
    }

    public PhysicalPort getPort() {
        return port;
    }

    public void setPort(PhysicalPort port) {
        this.port = port;
    }

    public PhysicalLine getLine() {
        return line;
    }

    public void setLine(PhysicalLine line) {
        this.line = line;
    }
    
    
}
