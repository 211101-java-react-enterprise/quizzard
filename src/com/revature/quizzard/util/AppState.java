package com.revature.quizzard.util;


import com.revature.quizzard.screens.WelcomeScreen;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
     * Encapsulation of application state we will create a few
     * things that will be used throughout the app
     *      - a common BufferedReader that all screens can use
     *      - a ScreenRouter that can be used to navigate
     *      - a Boolean that indicates if the app is still running
     *
     */
public class AppState {

    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {
        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader, router));

    }

    public void startup() {
        try {
            router.navigate("/welcome");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void shutdown() {

    }
}
