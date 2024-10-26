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
                currentV.getAdjList().keySet().stream().forEach(adjVertex->{
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
     * Dijkstra Algorithm find the shortest path. This algorithm running base on
     * BFS and calculate the shortest path to Destination Vertex from Start
     * Vertex.
     *
     * @param startV
     * @param desV
     */
    public void Dijkstra(NetworkDevice startV, NetworkDevice desV) {
        // Map <Vertex, Int> distance to store current total Distance to vertex
        HashMap<Vertex, Integer> distance = new HashMap<>();
        /*
        Set all vertices in Graph as key and their value is Infinite, except for
        start Vertex the value is 0
         */
        this.vertices
                .forEach(vertex -> {
                    distance.put(vertex, Integer.MAX_VALUE);
                });
        distance.put(this.getVertex(startV), 0);
        /*
        Map <Vertex, Vertex> previous store connection of 2 vertex as format
        Key: next vertex, Value: current vertex
         */
        HashMap<Vertex, Vertex> previous = new HashMap<>();
        previous.put(this.getVertex(desV), this.getVertex(startV));
        // Initiate Queue cause the Algorithm work base on BFS
        ArrayDeque<Vertex> queue = new ArrayDeque<>(19999);
        // Initiate Array store visisted Vertices
        ArrayList<Vertex> visitedArr = new ArrayList<>();
        // Enqueue StartV
        queue.addLast(this.getVertex(startV));
        // Loop until queue is not empty
        while (!queue.isEmpty()) {
            queue.stream()
                    .forEach(vertex -> {
                        System.out.print("{" + vertex.getLabel() + "," + distance.get(vertex) + "} ");
                    });
            System.out.println();
            // Get Queue header as current Vertex
            Vertex current = queue.pollFirst();
            // If vertex not Visited
            if (!visitedArr.contains(current)) {
                // Get adjList of Vertex
                current.getAdjList()
                        // For each entry
                        .forEach((adjVertex, Weight) -> {
                            // Calculate Current Total Distance
                            int totalDistance = distance.get(current) + Weight;
                            // If current Total Distance < distance of that vertex preivous
                            if (totalDistance < distance.get(adjVertex)) {
                                // Set distance of vertex as new total distance
                                distance.put(adjVertex, totalDistance);
                                // Put connection into previous Map
                                previous.put(adjVertex, current);
                            }
                            // Enqueue unvisited vertex        
                            if (!visitedArr.contains(adjVertex)) {
                                queue.addLast(adjVertex);
                            }
                        });
                // Add current vertex to visited array
                visitedArr.add(current);

            }
        }

    }

    /**
     * Dijkstra Algorithm but using Priority Queue.
     *
     * @param startV
     * @param desV
     */
    public void DijkstraRef(String startV, String desV) {
        // Map <Vertex, Int> distance to store current total Distance to vertex
        HashMap<Vertex, Integer> distance = new HashMap<>();
        /*
        Set all vertices in Graph as key and their value is Infinite, except for
        start Vertex the value is 0
         */
        this.vertices.stream()
                .forEach(vertex -> {
                    if (vertex.getLabel().equalsIgnoreCase(startV)) {
                        distance.put(vertex, 0);
                    } else {
                        distance.put(vertex, Integer.MAX_VALUE);
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
                return Integer.compare(distance.get(o1), distance.get(o2));
            }
        };

        // Initiate Queue cause the Algorithm work base on BFS
        PriorityQueue<Vertex> queue = new PriorityQueue<>(c);

        // Initiate Array store visisted Vertices
        ArrayList<Vertex> visitedArr = new ArrayList<>();
        // Enqueue StartV
        queue.add(this.getVertex(startV));
        // Loop until queue is not empty
        while (!queue.isEmpty()) {
            queue.forEach(vertex -> {
                System.out.print("{" + vertex.getLabel() + " - " + distance.get(vertex) + "} ");
            });
            System.out.println();
            // Get Queue header as current Vertex
            Vertex current = queue.poll();
            // If vertex not Visited
            if (!visitedArr.contains(current)) {
                // If at destination vertex
                if (current.getLabel().equalsIgnoreCase(desV)) {
                    // Break loop
                    break;
                }

                // Get adjList of Vertex
                current.getAdjList()
                        // For each entry
                        .forEach((adjVertex, Weight) -> {
                            // Calculate Current Total Distance
                            int totalDistance = distance.get(current) + Weight;
                            // If current Total Distance < distance of that vertex preivous
                            if (totalDistance < distance.get(adjVertex)) {
                                // Set distance of vertex as new total distance
                                distance.put(adjVertex, totalDistance);
                                // Put connection into previous Map
                                previous.put(adjVertex, current);
                            }
                            // Enqueue unvisited vertex        
                            if (!visitedArr.contains(adjVertex)) {
                                queue.add(adjVertex);
                            }
                        });
                // Add current vertex to visited array
                visitedArr.add(current);

            }
        }
        // Take right path 
        ArrayList<String> path = this.getPath(previous, startV, desV);
        // Reverse path order
        Collections.reverse(path);
        // Display to screen
        path.forEach(s -> {
            System.out.print(s + " ->");
        });
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
    public ArrayList<String> getPath(HashMap<Vertex, Vertex> previous, String startV, String destV) {
        // Initiate Vertex Key
        Vertex key = this.getVertex(destV);
        // Initiate Array String to store path 
        ArrayList<String> path = new ArrayList<>();
        // Add Destination vertex to path
        path.add(destV);
        // Begin loop while current is not startV
        while (!key.getLabel().equalsIgnoreCase(startV)) {
            // Initiate vertex Current store the value of Key
            Vertex current = previous.get(key);
            // Add vertex current's label to array
            path.add(current.getLabel());
            // Set key = current
            key = current;
        }
        return path;
    }

    /**
     * Prims Algorithm.
     */
    public void MST_Prims(String startV) {
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
