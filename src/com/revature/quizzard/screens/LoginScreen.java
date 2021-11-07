package com.revature.quizzard.screens;

import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class LoginScreen extends Screen{

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("Login Screen", "/login", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

    }
}
