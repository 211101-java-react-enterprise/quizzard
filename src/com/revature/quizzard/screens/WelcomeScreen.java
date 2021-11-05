package com.revature.quizzard.screens;

import java.io.BufferedReader;

public class WelcomeScreen extends Screen {

    public WelcomeScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("Welcome Screen", "/welcome", consoleReader);

    }

    @Override
    public void render() {

    }
}
