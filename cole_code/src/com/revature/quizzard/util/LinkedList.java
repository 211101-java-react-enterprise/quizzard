package com.revature.quizzard.util;

public class LinkedList<T> implements List<T> {

    private int size;
    private Node<T> head; //implicitly null
    private Node<T> tail; //you can explicitly declare as null but its not required


    @Override
    public boolean add(T data) {

        //not required bc some data structures allow null values
        if(data == null){
            return false;
        }

        Node<T> newNode = new Node<>(data);
        if (head == null) {
            //head == tail == newNode;
            head = newNode;
            tail = newNode;
        } else {
            tail.nextNode = newNode;
            tail = newNode;
        }
        size++;
        return true;
    }

    @Override
    public boolean contains(T data) {
        Node runner = head;
        while(runner != null){
            if(runner.data.equals(data)){
                return true;
            }
            else{
                runner = runner.nextNode;
            }
        }
        return false;
    }

    @Override
    public boolean isEmpty(T elem) {
        if(size==0) return true;
        else return false;
    }

    @Override
    public boolean remove(T elem) {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        return null;//return 0;
    }

    private static class Node<T>{
        T data;
        Node<T> nextNode;

        public Node(T data){
            this.data = data;
        }
    }
}
