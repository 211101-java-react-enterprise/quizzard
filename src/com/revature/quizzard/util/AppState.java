package com.revature.quizzard.util;

import com.revature.quizzard.screens.RegisterScreen;
import com.revature.quizzard.screens.WelcomeScreen;
import com.revature.quizzard.services.UserService;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
     * Encapsulation of application state we will create a few
     * things that will be used throughout the app
     *      - a common BufferedReader that all screens can use
     *      - a ScreenRouter that can be used to navigate between screens
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

        UserService userService = new UserService();

        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader, router, userService));

    }

    public void startup() {
        try {
            while (appRunning) {
                router.navigate("/welcome");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}
