/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Phan Sơn
 */
public class PhysicalLine implements Comparable<PhysicalLine> {

    private int bandwidth;
    private int latency;

    public PhysicalLine(int bandwidth, int latency) {
        this.bandwidth = bandwidth;
        this.latency = latency;
    }

    public int getBandwith() {
        return bandwidth;
    }

    public void setBandwith(int bandwith) {
        this.bandwidth = bandwith;
    }

    public int getLatency() {
        return latency;
    }

    public void setLatency(int latency) {
        this.latency = latency;
    }

    public double getWeight() {
        return this.latency / this.bandwidth;
    }

    @Override
    public int compareTo(PhysicalLine o) {
        double thisWeight = this.getWeight();
        double otherWeight = o.getWeight();
        return Double.compare(thisWeight, otherWeight);
    }
}
