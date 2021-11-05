package com.revature.quizzard.util;

public class TempDriver {

    public static void main(String[] args) {
        // Access static members by using the type that declared them
        List.staticMethodExample();
        LinkedList.staticMethodExample();
        List<String> myStringList = new LinkedList<>();
        myStringList.defaultMethodExample();
        String sResult = "";
        int sIndex = 0;

        myStringList.add("test-1");
        myStringList.add("test-2");
        myStringList.add("test-3");
        myStringList.remove("test-1");
        System.out.println(myStringList.contains("test-1")); // true
        System.out.println(myStringList.contains("test-2")); // false
        System.out.println(myStringList.contains("test-3")); // true
        System.out.println(myStringList.contains("test-123")); // false
        sResult = myStringList.get(sIndex);
        System.out.println(sResult);

    }

}
