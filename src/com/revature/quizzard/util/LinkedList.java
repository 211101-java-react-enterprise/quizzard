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

    // Following function finds an element in the list, and removes it.
    // Then takes the previous item and link it to the one following.
    // Returns a bool of if successful or not
    @Override
    public boolean remove(T element) {
        // Initialize the start of the list as well as a variable to store the previous node for linking
        Node<T> runner = head;
        Node<T> prevNode = null;
        // Boolean value to allow first object logic
        boolean firstTime = true;
        boolean performedRemove = false;

        //Iterate through the entire list
        while (runner != null) {
            // If we find something that's equal to the data we're looking for, delete and merge
            if (runner.data.equals(element)) {
                if(firstTime){
                    head = runner.nextNode;
                }
                else {
                    // Replace the link on the previous node with the node that comes after
                    prevNode.nextNode = runner.nextNode;
                }
                size = size - 1;
                performedRemove = true;
            }

            // Iterate to the next node, but keep a pointer for the previous node in runner.
            // sets first time to false from then on out
            prevNode = runner;
            runner = runner.nextNode;
            firstTime = false;
        }

        runner = head;
        while (runner != null) {
            runner = runner.nextNode;
        }


        // No node with matching data was found
        return performedRemove;
    }

    @Override
    public int size() {
        return size;
    }

    // This function moves to the item in a position [index] within a linked list and returns the value.
    @Override
    public T get(int index) {
        // Initialize a counter as well as a pointer to run through the linked list
        int counter = 0;
        Node<T> runner = head;

        // Make sure the index is in bounds
        if (index < size)
        {
            // Go thourgh the list until you get to the index
            while (counter < index){
                // Move on to the next node and iterate the counter
                runner = head.nextNode;
                counter = counter + 1;
            }
            // Return the appropriate item.
            return runner.data;
        }
        else {
            // Tell the user that they went out of bounds
            System.out.println("Index out of bounds!");
        }
        // Return null by default
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
