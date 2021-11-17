package com.revature.quizzard.util;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.screens.*;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.logging.Logger;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
    Encapsulate of application state. We will create a few things in here that will be used throughout the
    application:
        - a common BufferedReader that all screens can use to read from the console
        - a ScreenRouter that can be used to navigate from one screen to another
        - a boolean that indicates if the app is still running or not
 */
public class AppState {

    private final Logger logger;
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {

        logger = Logger.getLogger(true);
        logger.log("Initializing application...");

        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        FlashcardDAO cardDAO = new FlashcardDAO();
        FlashcardService cardService = new FlashcardService(cardDAO, userService);

        router.addScreen(new WelcomeScreen(consoleReader, router));
        router.addScreen(new RegisterScreen(consoleReader, router, userService));
        router.addScreen(new LoginScreen(consoleReader, router, userService));
        router.addScreen(new DashboardScreen(consoleReader, router, userService));
        router.addScreen(new FlashcardMenuScreen(consoleReader, router));
        router.addScreen(new FlashcardCreatorScreen(consoleReader, router, cardService));
        router.addScreen(new ViewMyFlashcardsScreen(consoleReader, router, cardService));

        logger.log("Application initialized!");

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
