package com.revature.quizzard.util.collections;

public interface Queue<T> extends Collection<T> {
    T poll();
    T peek();
}

