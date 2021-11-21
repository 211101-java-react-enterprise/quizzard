package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

import static com.revature.quizzard.util.AppState.shutdown;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super(consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        System.out.print("\nWelcome to Quizzard!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                router.navigate("/login");
                break;
            case "2":
                router.navigate("/register");
                break;
            case "3":
                System.out.println("Exiting application...");
                shutdown();
                break;
            case "throw exception":
                throw new RuntimeException(); // "throw" is used to explicitly throw an exception that will (hopefully) be handled elsewhere
            default:
                System.out.println("The user made an invalid selection");
        }

    }
}
