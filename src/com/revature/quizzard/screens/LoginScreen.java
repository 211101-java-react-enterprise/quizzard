package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;
import java.sql.SQLOutput;

// TODO: Implement me!
public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;

        }
    @Override
    public void render () throws Exception {
        System.out.println("The user selected Login");
        System.out.println("Please enter Username and Password");
        System.out.println("Username:");
        String userID = consoleReader.readLine();

        System.out.println("Password: ");
        String password = consoleReader.readLine();
    }

}
