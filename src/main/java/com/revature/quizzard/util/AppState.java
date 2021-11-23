package com.revature.quizzard.util;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.daos.FlashcardDAO;
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

    public AppState() {

        logger.info("Initializing application");

        appRunning = true;
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        FlashcardDAO cardDAO = new FlashcardDAO();
        FlashcardService cardService = new FlashcardService(cardDAO, userService);

        logger.info("Application initialized!");

    }

    public void startup() {

        try {
            while (appRunning) {
                // TODO something here probably
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shutdown() {
        appRunning = false;
    }
}
