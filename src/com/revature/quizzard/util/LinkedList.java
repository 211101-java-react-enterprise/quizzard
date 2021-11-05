package com.revature.quizzard.util;

public class LinkedList<T> implements List<T> {

    private int size;
    private Node<T> head; // implicitly null
    private Node<T> tail = null; // you can explicitly declare them as null, but it's not required.

    @Override
    public boolean add(T data) {

        // Not required, as some data structures do allow for null values.
        if (data == null) {
            return false;
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            tail = head = newNode;
        } else {
            tail = tail.nextNode = newNode;
        }

        size++;

        return true;

    }

    @Override
    public boolean contains(T data) {

        Node<T> runner = head;
        while (runner != null) {
            if (runner.data.equals(data)) {
                return true;
            }
            runner = runner.nextNode;
        }

        // No node with matching data was found
        return false;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    // TODO: IMPLEMENT ME!
    @Override
    public boolean remove(T element) {
        Node<T> runner = head;
        if (runner.data.equals(element)) {
            head = head.nextNode;
            size--;
            return true;
        }

        while (head != null) {
            if (runner.nextNode.data.equals(element)) {
                runner.nextNode = runner.nextNode.nextNode;
                size--;
                return true;
            }
            runner = runner.nextNode;
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    // TODO: IMPLEMENT ME!
    @Override
    public T get(int index) {

        // check if list is empty and just return null if is
        if (isEmpty()) return null;

        // if first index return head.data as head is index[0]
        if (index == 0) return head.data;

        // IndexOutOfBoundsException
        if (index > size) return null;

        Node<T> runner = head;

        //step through list until we get to wanted index
        for(int ii = 0; ii < index; ii++) {
            runner = runner.nextNode;
        }
        return runner.data;
    }

//    @Override // works without issue!
//    public void defaultMethodExample() {
//        System.out.println("This is an overridden default method.");
//    }

//    @Override // static methods inherited from interfaces CANNOT be overridden; but
    public static void staticMethodExample() {
        System.out.println("This is a method that shadows the static method declared by List");
    }

    // Nested Inner Class
    // The outer class (LinkedList) can see all of the members of this class
    private static class Node<T> {
        T data;
        Node<T> nextNode;

        public Node(T data) {
            this.data = data;
        }
    }

}
