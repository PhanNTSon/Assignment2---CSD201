/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 * Priority Queue. This ADT after add a new Element, it will base on it's 
 * priority to push it forward to the top.
 *
 * @author Phan Sơn
 * @param <T>
 */
public class LinkedPQueue<T extends Comparable<T>> {

    private Node<T> front;
    private Node<T> back;
    private int size, cap;

    public LinkedPQueue(int cap) {
        this.front = null;
        this.back = null;
        this.cap = cap;
        this.size = 0;
    }

    public Node<T> getFront() {
        return front;
    }

    public void setFront(Node<T> front) {
        this.front = front;
    }

    public Node<T> getBack() {
        return back;
    }

    public void setBack(Node<T> back) {
        this.back = back;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getCap() {
        return cap;
    }

    public void setCap(int cap) {
        this.cap = cap;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public boolean isFull() {
        return this.size == this.cap;
    }

    public void clear() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    public void display() {
        Node<T> current = this.front;
        while (current != null) {
            System.out.print(current.getData().toString() + ", ");
            current = current.getNext();
        }
        System.out.println();
    }

    /**
     * First, add new Element to Queue, then compare it with each Elements in
     * Queue to locate it's correct location in the Queue.
     * 
     * @param data 
     */
    public void enQueue(T data) {
        if (this.isFull()) {
            throw new IndexOutOfBoundsException();
        }
        // Initiate New Node
        Node<T> newNode = new Node(data);
        // Case Queue is empty
        if (this.isEmpty()) {
            // New node is both front and back of Queue
            this.front = newNode;
            this.back = newNode;
        // Case Queue is not empty
        } else {
            // Add New node to last
            this.back.setNext(newNode);
            newNode.setPrevious(this.back);
            this.back = newNode;
            Node<T> current = this.back;
            /*
            Trace back from the back to the top of Queue to locate location by 
            compare with each elements base on Priority.
            Because the New node is in the Back, therefore after finding 
            location successfully, only swap the Data but not the connection
            between Nodes.
            */
            while (current != this.front && data.compareTo(current.getPrevious().getData()) > 0) {
                current.setData(current.getPrevious().getData());
                current = current.getPrevious();
            }
            current.setData(data);
        }
        this.size++;
    }

    public T deQueue() {
        if (this.isEmpty()) {
            throw new ArrayStoreException();
        }
        T returnData = this.front.getData();
        if (this.size == 1) {
            this.back = null;
            this.front = null;
        } else {
            this.front = this.front.getNext();
            this.front.setPrevious(null);
        }
        this.size--;
        return returnData;
    }
}
