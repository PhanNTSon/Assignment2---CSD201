/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Phan Sơn
 */
public class Edge implements Comparable<Edge> {

    private Vertex start;
    private Vertex to;
    private int weight;

    public Edge(Vertex start, Vertex to, int weight) {
        this.start = start;
        this.to = to;
        this.weight = weight;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "Edge{" + this.start.getDevice() + ", " + this.to.getDevice() + ", weight=" + weight + '}';
    }

    /**
     *
     * @param o
     * @return -1 if smaller, 0 if equal, 1 if bigger
     */
    @Override
    public int compareTo(Edge o) {
        return Integer.compare(o.getWeight(), this.weight);
    }

}
