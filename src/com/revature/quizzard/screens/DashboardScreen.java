package com.revature.quizzard.screens;

import com.revature.quizzard.util.CurrentUser;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    CurrentUser currentUser;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, CurrentUser currentUser) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.currentUser = currentUser;
    }

    @Override
    public void render() throws Exception {
        String response = "Welcome" + currentUser.getUser();
        System.out.println(response);
    }

}
