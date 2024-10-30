/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 * Class Node represent as a Node in Queue or Stack that could hold any type of
 * data, therefore need to use Generic T.Because each Node can compare to each
 * other, therefore T need to implement interface Comparator to have method
 * compareTo() inside it for comparing. Therefore: T extends Comparable Class
 * Node also need to implement Comparable to compare.
 *
 * @author Phan Sơn
 * @param <T>
 */
public class Node<T extends Comparable<T>> implements Comparable<Node<T>> {

    private T data;
    private Node<T> next;
    private Node<T> previous;

    public Node(T data) {
        this.data = data;
        this.next = null;
        this.previous = null;
    }

    public T getData() {
        return data;
    }

    public Node<T> getNext() {
        return next;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setNext(Node<T> next) {
        this.next = next;
    }

    public Node<T> getPrevious() {
        return previous;
    }

    public void setPrevious(Node<T> previous) {
        this.previous = previous;
    }

    /**
     * Using compareTo() of T to return.
     *
     * @param o
     * @return
     */
    @Override
    public int compareTo(Node<T> o) {
        return this.data.compareTo(o.getData());
    }

}
