package com.revature.quizzard.screens;

import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.LinkedList;
import com.revature.quizzard.util.List;

import java.io.File;
import java.io.FileWriter;

public class RegisterScreen extends Screen{

    public RegisterScreen() {
        super("Register Screen", "Driver")
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

        System.out.println("Provided user first and last name: { \"firstName\": " + firstName + "\", lastName\": " + lastName + " }\n");
        System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s}\n", firstName, lastName);
        // String format specifiers: %s (strings), %d (whole numbers), %f (decimal values)

        AppUser newUser = new AppUser(firstName, lastName, email, username, password);
        System.out.println("Newly created user: " + newUser);

        File usersFile = new File("resources/data.txt");

        // the true argument here allows for us to append to the file, rather than completely overwriting it
        FileWriter fileWriter = new FileWriter(usersFile, true);
        fileWriter.write(newUser.toFileString() + "\n");
        fileWriter.close();

        String someData = "Wezley:Singleton:wezley.singleton@revature.com:wsingleton:password";
        String[] dataFragments = someData.split(":");
        // result = ["Wezley", "Singleton", "wezley.singleton@revature.com", "wsingleton", "password"]
        //              0           1                     2                        3            4

        for (int i = 0; i < dataFragments.length; i++) {
            System.out.println(dataFragments[i]);
        }

//        for (String fragment : dataFragments) {
//            System.out.println(fragment);
//        }

        // Interfaces are "abstract" constructs and cannot be directly instantiated
//        List myList = new List();

        // Covariance is allowed
        List<AppUser> myList = new LinkedList<>();
    }
}
