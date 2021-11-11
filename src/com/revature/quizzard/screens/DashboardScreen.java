package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

import static com.revature.quizzard.util.AppState.getUser;

public class DashboardScreen extends Screen {

    private AppUser user;

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
    }

    @Override
    public void render() throws Exception {
        System.out.println("DashboardScreen.render still under construction...");
        user = getUser();
        System.out.println(user.getUsername());
    }

}
