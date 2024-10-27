/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.HashMap;

/**
 *
 * @author Phan Sơn
 */
public class DNS {
    
    private HashMap<String, String> dnsRecords;

    public DNS() {
        this.dnsRecords = new HashMap<>();
    }
    
    public void addRecords(String domain, String ipAddr){
        this.dnsRecords.put(domain, ipAddr);
    }
    
    public void removeRecords(String domain){
        this.dnsRecords.remove(domain);
    }
    
    public void updateRecords(String domain, String newIpAddr){
        this.dnsRecords.put(domain, newIpAddr);
    }
    
    public String lookUp(String domain){
        return this.dnsRecords.get(domain);
    }
    
    
}
