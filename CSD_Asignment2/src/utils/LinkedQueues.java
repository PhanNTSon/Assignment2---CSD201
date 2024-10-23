/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Phan Sơn
 * @param <T>
 */
public class LinkedQueues<T extends Comparable<T>> {

    private Node<T> front;
    private Node<T> back;
    private int size;

    public LinkedQueues() {
        this.front = null;
        this.back = null;
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

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void clear() {
        this.front = null;
        this.back = null;
        this.size = 0;
    }

    public void display() {
        Node<T> current = this.front;
        while (current != null) {
            System.out.print(current.getData() + ", ");
            current = current.getNext();
        }
        System.out.println();
    }

    public void enQueue(T data) {
        Node<T> newNode = new Node(data);
        if (this.isEmpty()) {
            this.front = newNode;
            this.back = newNode;
        } else {
            this.back.setNext(newNode);
            this.back = newNode;
        }
        this.size++;
    }

    public T deQueue() {
        if (this.isEmpty()) {
            return null;
        }
        T returnData = this.front.getData();
        if (this.size == 1) {
            this.back = null;
            this.front = null;
        } else {
            this.front = this.front.getNext();
        }
        this.size--;
        return returnData;
    }

}
