package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

// TODO: Implement me!

public class LoginScreen extends Screen {
    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("Please enter an existing username and password");
        System.out.print("USERNAME: ");
        String username = consoleReader.readLine();
        System.out.print("\nPASSWORD: ");
        String password = consoleReader.readLine();
        System.out.println();

        // Add new BufferedReader - point to file versus System.in
        // We have Console input (above) and File input
        // Compare Console In to File In - with Authenticate
        AppUser user = UserService.auhenticateUser(username, password);
    }
}
