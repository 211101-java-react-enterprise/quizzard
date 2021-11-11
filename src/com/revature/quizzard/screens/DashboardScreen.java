package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {
    private AppUser AuthenticatedUser;
    
    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router, AppUser AutheticatedUser) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
        this.AuthenticatedUser=AutheticatedUser;

    }

    @Override
    public void render() throws Exception {
        System.out.println("Welcome to Quizzard"+AuthenticatedUser.getUsername());
    }

}
