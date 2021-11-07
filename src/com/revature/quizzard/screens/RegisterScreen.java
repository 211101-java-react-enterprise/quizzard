package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.util.LinkedList;
import com.revature.quizzard.util.List;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;

public class RegisterScreen extends Screen{

    private final UserService userService;

    public RegisterScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService) {

        super("Register Screen", "/register", consoleReader, router);
        this.userService = userService;

    }

    @Override
    public void render() throws Exception {
        System.out.println("The user selected Register");
        System.out.println("Please provide us with some basic information.");
        System.out.print("First name: ");
        String firstName = consoleReader.readLine();

        System.out.print("Last name: ");
        String lastName = consoleReader.readLine();

        System.out.print("Email: ");
        String email = consoleReader.readLine();

        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("Password: ");
        String password = consoleReader.readLine();


        System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s}\n", firstName, lastName);


        AppUser newUser = new AppUser(firstName, lastName, email, username, password);

        boolean registerSuccessful = userService.registerNewUser(newUser);

        if (registerSuccessful) {
            // router.navigate("/dashboard");
        } else {
            System.out.println("You have provided invalid data. Please try again.");
        }
    }
}
