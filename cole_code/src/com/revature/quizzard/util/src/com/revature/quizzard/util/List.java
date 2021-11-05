package com.revature.quizzard.util;

/**
 * Interfaces
 *
 *      - act as a "contract" for implementing concrete classes (any class that's not abstract)
 *      layman's terms: any class that implements this interface will be
 *      required to provide logic for the declared methods of this interface
 *
 *      these do not have constructors and therefore cannot be instantiated
 *
 *      interface declaration is implicitly abstract
 *
 *      all method stubs declared within an interface are also implicitly abstract
 */
interface List<T> extends Collection<T> {
    //List myList = new LinkedList(); //can use covariant data types
    //List list = new List();
    //this does not work bc interfaces don't allow constructors

    T get(int index); //this is a method stub


    default void defaultExample(){

    }
}
