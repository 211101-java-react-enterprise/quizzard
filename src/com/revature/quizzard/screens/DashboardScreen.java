package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
    }

    private static AppUser LoggedInUser;

    public static void setLoggedInUser(AppUser loggedInUser) {
        LoggedInUser = loggedInUser;
    }

    @Override
    public void render() throws Exception {
        System.out.println("Welcome " + LoggedInUser.getFirstName() + "!");
        String testInput = consoleReader.readLine();
    }

}
