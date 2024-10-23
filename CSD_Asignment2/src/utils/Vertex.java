/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import java.util.HashMap;

/**
 *
 * @author ADMIN
 */
public class Vertex implements Comparable<Vertex> {

    private String label;
    private HashMap<Vertex, Integer> adjList;

    public Vertex(String lable) {
        this.label = lable;
        this.adjList = new HashMap<>();
    }

    public String getLabel() {
        return label;
    }

    public HashMap<Vertex, Integer> getAdjList() {
        return adjList;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public int compareTo(Vertex o) {
        return -1;
    }
}
