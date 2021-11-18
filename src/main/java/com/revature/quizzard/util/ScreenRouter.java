package com.revature.quizzard.util;

import com.revature.quizzard.screens.Screen;
import com.revature.quizzard.util.collections.LinkedList;

import java.util.stream.Collectors;

public class ScreenRouter {

    private final LinkedList<Screen> screens;

    public ScreenRouter() {
        screens = new LinkedList<>();
    }

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    public void navigate(String route) throws Exception {
//        for (int i = 0; i < screens.size(); i++) {
//            Screen thisScreen = screens.get(i);
//            if (thisScreen.getRoute().equals(route)) {
//                thisScreen.render();
//            }
//        }
//        throw new RuntimeException("No screen found with provided route");

        // Does the same thing as the above logic
       screens.stream()
              .filter(screen -> screen.getRoute().equals(route))
              .findFirst()
              .orElseThrow(() -> new RuntimeException("No screen found with provided route"))
              .render();


    }
}
