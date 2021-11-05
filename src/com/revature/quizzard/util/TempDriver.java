package com.revature.quizzard.util;

public class TempDriver {

    public static void main(String[] args) {
        // Access static members by using the type that declared them
        List.staticMethodExample();
        LinkedList.staticMethodExample();
        List<String> myStringList = new LinkedList<>();
        myStringList.defaultMethodExample();

        myStringList.add("test-1");
        myStringList.add("test-2");
        myStringList.add("test-3");
        System.out.println(myStringList.contains("test-1")); // true
        System.out.println(myStringList.contains("test-2")); // true
        System.out.println(myStringList.contains("test-3")); // true
        System.out.println(myStringList.contains("test-123")); // false

        System.out.println();
        System.out.println(myStringList.get(0));
        System.out.println(myStringList.get(4));

        myStringList.remove("test-2");
        System.out.println();
        System.out.println(myStringList.get(1));

    }

}
