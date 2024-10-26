/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

/**
 *
 * @author Phan Sơn
 */
public class PhysicalLine implements Comparable<PhysicalLine> {
    private int numOfPortFrom;
    private int bandwith;
    private int latency;

    public PhysicalLine(int numOfPortFrom, int bandwith, int latency) {
        this.numOfPortFrom = numOfPortFrom;
        this.bandwith = bandwith;
        this.latency = latency;
    }

    public int getNumOfPortFrom() {
        return numOfPortFrom;
    }

    public void setNumOfPortFrom(int numOfPortFrom) {
        this.numOfPortFrom = numOfPortFrom;
    }

    public int getBandwith() {
        return bandwith;
    }

    public void setBandwith(int bandwith) {
        this.bandwith = bandwith;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }
    
    public double getWeight(){
        return this.latency / this.bandwith;
    }
    
    @Override
    public int compareTo(PhysicalLine o) {
        double thisWeight = this.getWeight();
        double otherWeight = o.getWeight();
        return Double.compare(thisWeight, otherWeight);
    }
}
