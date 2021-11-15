package com.revature.quizzard.util.collections;

public interface Set<T> extends Collection<T> {

    boolean add(T data);
    boolean contains(T data);
    boolean remove(T data);
    int size();

}
