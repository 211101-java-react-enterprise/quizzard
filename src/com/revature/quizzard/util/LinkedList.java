package com.revature.quizzard.util;

import java.lang.IndexOutOfBoundsException;

//*************************************************

public class LinkedList<T> implements List<T> {

    //0000000000000000000000000000000000000000000000000

    private int size;
    private Node<T> head; // implicitly null
    private Node<T> tail = null; // you can explicitly declare them as null, but it's not required.

    //0000000000000000000000000000000000000000000000000

    //-------------------------------------------------

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
            tail.nextNode = newNode;
            tail = newNode;
        }

        size++;

        return true;

    }

    //-------------------------------------------------

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

    //-------------------------------------------------

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    //-------------------------------------------------

    // TODO: IMPLEMENT ME!
    @Override
    public boolean remove(T element) {
        // check edge case of requested value being in head first
        if (head.data.equals(element)) {
            head = head.nextNode;
            size--;
            return true;
        }

        Node<T> runner = head;
        Node<T> preRunner = head; // added to move tail properly and easily, init to head in case only 2 elements exist

        // since we are checking nextNodes value we use that in our while loop
        while (runner.nextNode != null) {
            // check runner.nextNode's data for equality as we need to change the currentNode.nextNode
            if (runner.nextNode.data.equals(element)) {
                runner.nextNode = runner.nextNode.nextNode;
                size--;
                return true;
            }

            preRunner = runner;
            runner = runner.nextNode;

        }

        //check tail as separate edge case
        if (tail.data.equals(element)) {
            tail = preRunner;
            tail.nextNode = null;

            size--;

            return true;

        }

        return false;
    }

    //-------------------------------------------------

    @Override
    public int size() {
        return size;
    }

    //-------------------------------------------------

    @Override
    public T get(int index) {

        // check if list is empty and check if index is out of bounds and throw exception if necessary
        if (index >= size || index < 0 || isEmpty()) {
            throw new IndexOutOfBoundsException("Requested index is out of bounds");
        }

        // if first index return head.data as head is index[0]
        if (index == 0) return head.data;

        Node<T> runner = head;

        //step through list until we get to wanted index
        for(int ii = 0; ii < index; ii++) {
            runner = runner.nextNode;
        }
        return runner.data;
    }

    //-------------------------------------------------

//    @Override // works without issue!
//    public void defaultMethodExample() {
//        System.out.println("This is an overridden default method.");
//    }

    //-------------------------------------------------

//    @Override // static methods inherited from interfaces CANNOT be overridden; but
    public static void staticMethodExample() {
        System.out.println("This is a method that shadows the static method declared by List");
    }

    //-------------------------------------------------

    //*************************************************

    // Nested Inner Class
    // The outer class (LinkedList) can see all of the members of this class
    private static class Node<T> {
        T data;
        Node<T> nextNode;

        public Node(T data) {
            this.data = data;
        }
    }

    //*************************************************

}
