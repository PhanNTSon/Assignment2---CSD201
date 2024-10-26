/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

import data.NetworkDevice;
import data.PhysicalLine;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

/**
 *
 * @author Phan Sơn
 */
public class Graph {

    private Set<Vertex> vertices;

    public Graph() {
        this.vertices = new HashSet<>();
    }

    /**
     * Find a specific Vertex in set Vertices.
     *
     * @param device
     * @return
     */
    public Vertex getVertex(NetworkDevice device) {
        // First turn Set into Stream
        return this.vertices.stream()
                // Then find all vertex that have same lable
                .filter(vertex -> vertex.getDevice().getClass().equals(device.getClass()))
                // Take out the first Element 
                .findFirst()
                // If there is no Element sastified filter, return null
                .orElse(null);
    }

    /**
     * Add new vertex to Graph.
     *
     * @param device
     */
    public void addVertex(NetworkDevice device) {
        vertices.add(new Vertex(device));
    }

    /**
     * Get 2 vertices in Graph and connect it through the number of port on 2
     * vertex.
     *
     * @param device1
     * @param device2
     * @param portNumOfDevice1
     * @param portNumOfDevice2
     * @param latenecy
     * @param bandwith
     */
    public void addEdge(NetworkDevice device1, NetworkDevice device2,
            int portNumOfDevice1, int portNumOfDevice2,
            int latenecy, int bandwith) {

        Vertex v1 = this.getVertex(device1);
        Vertex v2 = this.getVertex(device2);

        // If v1 is null means no Vertex have device1 in Graph
        if (v1 == null) {
            // Add new Vertex v1 to Graph
            this.addVertex(device1);
            // Set v1 as that new Vertex
            v1 = this.getVertex(device1);
        }

        // If v2 is null means no Vertex have label v2 in Graph
        if (v2 == null) {
            // Add new Vertex v2 to Graph
            this.addVertex(device2);
            // Set v2 as that new Vertex
            v2 = this.getVertex(device2);
        }

        // Connect 2 vertices by adding to their adjList
        // Add v2 to v1's adjList with port's num
        v1.getAdjList().put(v2, new PhysicalLine(portNumOfDevice1, bandwith, latenecy));
        // Add v1 to v2's adjList with port's num
        v2.getAdjList().put(v1, new PhysicalLine(portNumOfDevice2, bandwith, latenecy));
    }

    /**
     * Display Graph by showing all Vertices in Graph and its connection.
     */
    public void display() {
        this.vertices.forEach(vertex -> {
            System.out.println(vertex.toString());
        });
    }

    /**
     * Traversal Graph by BFS Algorithm. The mechanism work like traversal a
     * tree but this time there are 2 differences: First, the root could be any
     * vertex Second, only visit vertex once.
     *
     * @param vertexStart
     */
    public void BFS(NetworkDevice vertexStart) {
        LinkedQueues<Vertex> vertexQueue = new LinkedQueues<>();
        Set<Vertex> visitedArr = new HashSet<>(); // Reduce time to O(1)
        Vertex startVertex = this.getVertex(vertexStart);
        vertexQueue.enQueue(startVertex);

        // Loop while Queue is not empty
        while (!vertexQueue.isEmpty()) {
            // Take out the header of Queue as current vertex 
            Vertex currentV = vertexQueue.deQueue();
            // If vertex is not visited before
            if (!visitedArr.contains(currentV)) {
                System.out.print(currentV.getDevice().getName() + " ");

                visitedArr.add(currentV);
                // Add all vertices that connected to current vertex
                currentV.getAdjList().keySet()
                        .stream()
                        .forEach(adjVertex -> {
                            vertexQueue.enQueue(adjVertex);
                        });
            }
        }
    }

    /**
     * Traversal Graph using DFS Loop. Same mechanism as BFS but using Stack
     * instead of Queue
     *
     * @param vertexStart
     */
    public void DFS(NetworkDevice vertexStart) {
        LinkedStack<Vertex> vertexStack = new LinkedStack<>();
        Set<Vertex> visitedArr = new HashSet<>(); // Reduce time to O(1)
        Vertex startVertex = this.getVertex(vertexStart);
        vertexStack.push(startVertex);

        // Loop until Queue empty
        while (!vertexStack.isEmpty()) {
            Vertex currentV = vertexStack.pop();
            // If vertex is not visited before
            if (!visitedArr.contains(currentV)) {
//                System.out.print(currentV.getLabel() + " ");
                visitedArr.add(currentV);
                // Add all vertices that connected to current vertex
                currentV.getAdjList().keySet().stream().forEach(adjVertex -> {
                    vertexStack.push(adjVertex);
                });

            }
        }
    }

    /**
     * DFS Using Recursion.
     *
     * @param startVertex
     * @param visitedArr
     */
    public void DFS_R(String startVertex, ArrayList<Vertex> visitedArr) {
        // Get current Vertex
        // If current Vertex is not visited
        // 

        /*
        Vertex start = this.getVertex(startVertex);
        visited.add(start);
        System.out.print(start.label);
        start.adjList.entrySet().forEach(entry ->
        {
            if (!visited.contains(entry.getKey())) {
                DFSRecursive(entry.getKey().label, visited);
            }
        });
         */
    }

    /**
     * Dijkstra Algorithm but using Priority Queue.
     *
     * @param startDevice
     * @param desDevice
     * @return
     */
    public ArrayList<Vertex> DijkstraRef(NetworkDevice startDevice, NetworkDevice desDevice) {
        HashMap<Vertex, Double> distance = new HashMap<>();
        Vertex startV = this.getVertex(startDevice);

        // Loop through set vertices, set tag distance of every Vertex is Infitie except startVertex
        this.vertices.stream()
                .forEach(vertex -> {
                    if (vertex.getDevice().compareTo(startDevice) == 0) {
                        distance.put(vertex, Double.valueOf("0"));
                    } else {
                        distance.put(vertex, Double.MAX_VALUE);
                    }
                });
        /*
        Map <Vertex, Vertex> previous store connection of 2 vertex as format
        Key: next vertex, Value: current vertex
         */
        HashMap<Vertex, Vertex> previous = new HashMap<>();

        // Initiate Comparerator to Compare each total distance in queue
        Comparator<Vertex> c = new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return Double.compare(distance.get(o1), distance.get(o2));
            }
        };

        PriorityQueue<Vertex> verticesPQueue = new PriorityQueue<>(c);

        ArrayList<Vertex> visitedArr = new ArrayList<>();
        verticesPQueue.add(startV);

        // Loop until queue is not empty
        while (!verticesPQueue.isEmpty()) {

            Vertex current = verticesPQueue.poll();

            // If vertex not Visited
            if (!visitedArr.contains(current)) {
                // If at destination vertex
                if (current.getDevice().compareTo(desDevice) == 0) {
                    // Break loop
                    break;
                }
                // For each entry Calculate Current Total Distance
                current.getAdjList()
                        .forEach((adjVertex, line) -> {
                            double totalDistance = distance.get(current) + line.getWeight();
                            // If current Total Distance < distance of that vertex preivous
                            if (totalDistance < distance.get(adjVertex)) {
                                distance.put(adjVertex, totalDistance);
                                previous.put(adjVertex, current);
                            }

                            // If vertex is not visited then add to queue
                            if (!visitedArr.contains(adjVertex)) {
                                verticesPQueue.add(adjVertex);
                            }
                        });
                visitedArr.add(current);

            }
        }

        Vertex desV = this.getVertex(desDevice);
        ArrayList<Vertex> path = this.getPath(previous, startV, desV);
        // Reverse path order
        return path;
    }

    /**
     * Get the path from start Vertex to destination Vertex base on Map. The
     * method trace back from Destination Vertex to Start Vertex and each time
     * it do so, add the linked vertex to array.
     *
     * @param previous
     * @param startV
     * @param destV
     * @return
     */
    public ArrayList<Vertex> getPath(HashMap<Vertex, Vertex> previous, Vertex startV, Vertex destV) {

        ArrayList<Vertex> path = new ArrayList<>();
        path.add(destV);

        // Begin loop until Vertex destV is equal to Vertex startV
        while (destV.compareTo(startV) != 0) {
            Vertex current = previous.get(destV);
            path.add(current);
            destV = current;
        }
        Collections.reverse(path);
        return path;
    }

    /**
     * Prims Algorithm.
     */
    public void MST_Prims(NetworkDevice startDevice) {
        // Collected edges
        List<Edge> mst = new ArrayList<>();
        // Visited Array store Visited Vertex
        List<Vertex> visitedV = new ArrayList<>();
        // Priority Queue store edges
        LinkedPQueue<Edge> queue = new LinkedPQueue<>(1000000);
        // Vertex Start enqueue
        Vertex s = this.getVertex(startV);
        s.getAdjList().entrySet().
                forEach(entry -> {
                    queue.enQueue(new Edge(s, entry.getKey(), entry.getValue()));
                });
        visitedV.add(s);
        // While Queue is not Empty
        while (!queue.isEmpty()) {
            // Take out smallest Edge 
            Edge current = queue.deQueue();
            // Take out vertex To of current Edge
            Vertex vTo = current.getTo();
            // Check if vTo is visited
            if (!visitedV.contains(vTo)) {
                // If not then add edge to list and add vTo in visited
                mst.add(current);
                visitedV.add(vTo);
                vTo.getAdjList().entrySet()
                        .forEach(entry -> {
                            queue.enQueue(new Edge(vTo, entry.getKey(), entry.getValue()));
                        });
            }

        }
        mst.forEach(edge -> {
            System.out.println(edge.toString());
        });
    }

    public Graph MST_Kruskal() {
        Graph returnGraph = new Graph();
        List<Vertex> visitedV = new ArrayList<>();
        LinkedPQueue<Edge> queue = new LinkedPQueue<>(100000);
        this.vertices.forEach(vertex -> {
            vertex.getAdjList().entrySet()
                    .forEach(entry -> {
                        queue.enQueue(new Edge(vertex, entry.getKey(), entry.getValue()));
                    });
        });
        while (!queue.isEmpty()) {
            Edge current = queue.deQueue();
            Vertex v1 = current.getStart();
            Vertex v2 = current.getTo();
            if (!visitedV.contains(v1) || !visitedV.contains(v2)) {
                returnGraph.addEdge(current.getStart().getLabel(), current.getTo().getLabel(), current.getWeight());
            }

            if (!visitedV.contains(v1)) {
                visitedV.add(v1);
            }
            if (!visitedV.contains(v2)) {
                visitedV.add(v2);
            }
        }
        return returnGraph;
    }

    public boolean checkHaveEulerCircuit() {
        for (Vertex v : this.vertices) {
            if (v.getAdjList().size() % 2 != 0 || v.getAdjList().size() == 0) {
                return false;
            }
        }
        return true;
    }

    public boolean checkHaveEulerPath() {
        int count = 0;
        for (Vertex v : this.vertices) {
            if (v.getAdjList().size() % 2 != 0 || v.getAdjList().size() == 0) {
                if (count > 2) {
                    return false;
                } else {
                    count++;
                }
            }
        }
        return true;
    }

    public List<String> getEulerPath(String startV, Graph graph) {
        Graph newG = graph;
        if (!this.checkHaveEulerPath()) {
            return null;
        }
        List<String> eulerPath = new ArrayList<>();
        // Using DFS, therefore using Stack
        LinkedStack<Vertex> stack = new LinkedStack<>();

        Vertex sV = newG.getVertex(startV);
        // Push in sV to stack
        stack.push(sV);

        // Begin loop
        while (!stack.isEmpty()) {
            Vertex current = stack.pop();
            eulerPath.add(current.getLabel());
            current.getAdjList().entrySet()
                    .forEach(entry -> {

                    });
        }

        return eulerPath;
    }

    public List<String> getEulerCircuit(String startV, Graph graph) {
        Graph newG = graph;
        if (!this.checkHaveEulerCircuit()) {
            return null;
        }
        List<String> eulerCircuit = new ArrayList<>();

        return eulerCircuit;
    }
}
