package com.revature.quizzard.util;

import com.revature.quizzard.screens.Screen;

//*************************************************

public class ScreenRouter {

    //0000000000000000000000000000000000000000000000000

    private final LinkedList<Screen> screens;

    //0000000000000000000000000000000000000000000000000

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    public ScreenRouter() {
        screens = new LinkedList<>();
    }

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    //-------------------------------------------------

    public void addScreen(Screen screen) {
        screens.add(screen);
    }

    //-------------------------------------------------

    public void navigate(String route) throws Exception {
        for (int i = 0; i < screens.size(); i++) {
            Screen thisScreen = screens.get(i);
            if (thisScreen.getRoute().equals(route)) {
                thisScreen.render();
            }
        }
    }

    //-------------------------------------------------

}
