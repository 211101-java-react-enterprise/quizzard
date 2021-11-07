package com.revature.zrin.util;

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

        Node<T> newNode = new Node<>(data); // Node<T> being declared at the end of this file
        // 5.1 @ 1h1m16s
        if (head == null) {
            // Head is the bottom plate. When the stack is one plate high, it is the head and tail.
            tail = head = newNode;
        } else {
            // Tail is the top plate. It changes each time we add a new plate to the stack.
            tail.nextNode = newNode; // Introduce the existing top plate to the new plate
            tail = newNode; // Place the new plate on top of the stack
        }

        size++; // Inform this list's Size Baron that the number of elements in our array has increased by one.

        return true; // Placating the compiler

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
        if (element == null) {
            return false;
        } else {}

        // WE NEED TO DO GET FIRST

        return true;

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        // Error handling - make sure $index represents a number within the existing list
        if (index < 0 || index >= size) {
            throw new RuntimeException("Provided index is out of bounds");
        } else { }

        Node<T> currentNode = head; // A Node object we use to hold the current item in the list
        // Starting with 0, we iterate through the list.
        for (int i = 0; i <= index; i++) {
            if (i == index) { // When the current iteration matches the requested number, we've got it!
                return currentNode.data;
            } else {
                currentNode = currentNode.nextNode; // ...otherwise, use the nextNode method of Node<T> to visit the next guy
            }
        }

        return null;

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
