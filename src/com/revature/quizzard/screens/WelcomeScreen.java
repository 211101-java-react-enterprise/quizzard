package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

import static com.revature.quizzard.util.AppState.shutdown;

//*************************************************

public class WelcomeScreen extends Screen {

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("Welcome Screen", "/welcome", consoleReader, router);

    }

    //TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT

    //-------------------------------------------------

    @Override
    public void render() throws Exception {

        System.out.print("Welcome to Quizzard!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n" +
                "> ");

        String userSelection = consoleReader.readLine();

        switch (userSelection) {
            case "1":
                //login();
                break;
            case "2":
                router.navigate("/register");
                break;
            case "3":
                System.out.println("Exiting application...");
//                        System.exit(0); // BAD! don't do it. Halts the JVM abruptly. Considered to be bad practice
                shutdown();
                break;
            case "throw exception":
                throw new RuntimeException(); // "throw" is used to explicitly throw an exception that will (hopefully) be handled elsewhere
            default:
                System.out.println("The user made an invalid selection");
        }
    }

    //-------------------------------------------------

}
