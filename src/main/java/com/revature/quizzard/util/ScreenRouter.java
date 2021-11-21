package com.revature.quizzard.util;

import com.revature.quizzard.screens.View;

import java.util.HashMap;
import java.util.Map;

public class ScreenRouter {

    private final HashMap<String, View> screens;

    public ScreenRouter() {
        screens = new HashMap<>();
    }

    public void addView(String route, View view) {
        screens.put(route, view);
    }

    public void navigate(String route) throws Exception {

       screens.entrySet()
              .stream()
              .filter(entry -> entry.getKey().equals(route))
              .map(Map.Entry::getValue)
              .findFirst()
              .orElseThrow(() -> new RuntimeException("No screen found with provided route"))
              .render();


    }
}
