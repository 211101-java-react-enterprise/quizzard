package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.ScreenRouter;
import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;

import java.io.BufferedReader;

// TODO: Implement me!
public class LoginScreen extends Screen {
    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("The user selected Login");
        System.out.print("Username: ");
        String givenUser = consoleReader.readLine();

        System.out.print("Password: ");
        String givenPassword = consoleReader.readLine();

        System.out.println("Fetching user...");


        AppUser authUser = userService.authenticateUser(givenUser,givenPassword);

        if (authUser != null) {
            System.out.println("Login successful!");
            // router.navigate("/dashboard");
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }


    }
}
