/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author Phan Sơn
 */
public class LinkedStack<T extends Comparable<T>> {
    private Node<T> top;
    private int size;

    public LinkedStack() {
        this.top = null;
        this.size = 0;
    }
    
    public boolean isEmpty(){
        return this.size == 0;
    }
    
    public void clear(){
        this.top = null;
        this.size = 0;
    }
    
    public void display(){
        for (Node i = this.top; i != null; i = i.getNext()) {
            System.out.print(i.getData() + " ");
        }
    }
    
    public void push(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.setNext(this.top);
        this.top = newNode;
        this.size ++;
    }
    
    public T pop(){
        if (this.isEmpty()) {
            throw new ArrayStoreException("STACK EMPTY");
        }
        T data = this.top.getData();
        this.top = this.top.getNext();
        this.size--;
        return data;
    }
    
    public T peek(){
        if (this.isEmpty()) {
            throw new ArrayStoreException("STACK EMPTY");
        }
        T data = this.top.getData();
        return data;
    }
    
    public int search(T data){
        if (this.isEmpty()) {
            throw new ArrayStoreException("STACK EMPTY");
        }
        
        int count = 0;
        Node current = this.top;
        while (current != null) {
            if (current.getData() == data){
                return count;
            }
            current = current.getNext();
            count++;
        }
        return -1;
    }
}
