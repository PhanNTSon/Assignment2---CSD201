/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class DataPacket {
    private String srcIP;
    private String destIP;
    private String srcMAC;
    private String destMAC;
    private int ttl;
    private String contentData;

    public DataPacket() {
    }

    public DataPacket(String srcIP, String destIP, String srcMAC, String destMAC, int ttl) {
        this.srcIP = srcIP;
        this.destIP = destIP;
        this.srcMAC = srcMAC;
        this.destMAC = destMAC;
        this.ttl = ttl;
    }

    public String getSrcIP() {
        return srcIP;
    }

    public void setSrcIP(String srcIP) {
        this.srcIP = srcIP;
    }

    public String getDestIP() {
        return destIP;
    }

    public void setDestIP(String destIP) {
        this.destIP = destIP;
    }

    public String getSrcMAC() {
        return srcMAC;
    }

    public void setSrcMAC(String srcMAC) {
        this.srcMAC = srcMAC;
    }

    public String getDestMAC() {
        return destMAC;
    }

    public void setDestMAC(String destMAC) {
        this.destMAC = destMAC;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }

    public String getContentData() {
        return contentData;
    }

    public void setContentData(String contentData) {
        this.contentData = contentData;
    }
    
}
