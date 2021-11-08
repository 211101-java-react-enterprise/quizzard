package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.services.VerifyService;
import com.revature.quizzard.util.ScreenRouter;

import java.io.BufferedReader;

public class LoginScreen extends Screen {
    private final VerifyService verifyService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, VerifyService verifyService) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("The user selected Login");
        System.out.print("Username: ");
        String username = consoleReader.readLine();

        System.out.print("password: ");
        String password = consoleReader.readLine();

       //System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s}\n", firstName, lastName);
        // String format specifiers: %s (strings), %d (whole numbers), %f (decimal values)

        //AppUser newUser = new AppUser(firstName, lastName, email, username, password);

        VerifyService verify_user = new VerifyService(username, password);

        boolean loginSuccess = true;//code to verify credentials

        if (loginSuccess) {
            router.navigate("");
            // router.navigate("/dashboard");
        } else {
            System.out.println("You have provided invalid data. Please try again.");
        }




    }
}
