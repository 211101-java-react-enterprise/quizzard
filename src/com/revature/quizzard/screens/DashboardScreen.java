package com.revature.quizzard.screens;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class DashboardScreen extends Screen {

    public DashboardScreen(BufferedReader consoleReader, ScreenRouter router) {
        super("DashboardScreen", "/dashboard", consoleReader, router);
    }

    @Override
    public void render() throws Exception {

        //display validated username or first name
        //object that has our information
        AppUser newUser = new AppUser();
        AppUserDAO user = new AppUserDAO();
        System.out.println(user.findUserByUsernameAndPassword(newUser.getUsername(), newUser.getPassword().toString()));
    }

}
