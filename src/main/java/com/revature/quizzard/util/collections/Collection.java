package com.revature.quizzard.util.collections;

import java.util.stream.Stream;
import java.util.stream.StreamSupport;

// <T> is an example of a parameterized type
// The value of this type will be determined at object instantiation (aka when we call a constructor for a concrete implementation)
public interface Collection<T> extends Iterable<T> {

    boolean add(T element);
    boolean contains(T element);
    boolean isEmpty();
    boolean remove(T element);
    int size();

    default Stream<T> stream() {
        return StreamSupport.stream(spliterator(), false);
    }

}
