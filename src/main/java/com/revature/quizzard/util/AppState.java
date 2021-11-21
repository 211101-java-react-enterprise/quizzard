package com.revature.quizzard.util;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
import com.revature.quizzard.screens.*;
import com.revature.quizzard.services.FlashcardService;
import com.revature.quizzard.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    private final Logger logger = LogManager.getLogger();
    private static boolean appRunning;
    private final ScreenRouter router;

    public AppState() {

        logger.info("Initializing application");

        appRunning = true;
        router = new ScreenRouter();
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        FlashcardDAO cardDAO = new FlashcardDAO();
        FlashcardService cardService = new FlashcardService(cardDAO, userService);

        router.addView("/test", () -> System.out.println("This is a test/placeholder screen."));
        router.addView("/welcome", new WelcomeScreen(consoleReader, router));
        router.addView("/register", new RegisterScreen(consoleReader, router, userService));
        router.addView("/login", new LoginScreen(consoleReader, router, userService));
        router.addView("/dashboard", new DashboardScreen(consoleReader, router, userService));
        router.addView("/flashcards", new FlashcardMenuScreen(consoleReader, router));
        router.addView("/create-flashcards", new FlashcardCreatorScreen(consoleReader, router, cardService));
        router.addView("/my-flashcards", new ViewMyFlashcardsScreen(consoleReader, router, cardService));

        logger.info("Application initialized!");

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
