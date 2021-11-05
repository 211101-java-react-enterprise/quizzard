package com.revature.quizzard.util;

public interface Collection <T> {

    boolean add(T elem);
    boolean contains(T elem);
    boolean isEmpty(T elem);
    boolean remove(T elem);
    int size(); //how many things are insideof it

}
