package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

// TODO: Implement me!
public class LoginScreen extends Screen {


    public LoginScreen(BufferedReader consoleReader, ScreenRouter router) {
        super( "LoginScreen", "/login", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        System.out.print("Username: ");
        String username = consoleReader.readLine();
        System.out.print("Password: ");
        String password = consoleReader.readLine();


    }
}
