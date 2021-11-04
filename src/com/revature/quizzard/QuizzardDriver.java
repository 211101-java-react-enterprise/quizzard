package com.revature.quizzard;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Scanner;

public class QuizzardDriver {

    static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {

        boolean isRunning = true;

        while (isRunning) {

            System.out.print("Welcome to Quizzard!\n" +
                    "1) Login\n" +
                    "2) Register\n" +
                    "3) Exit\n" +
                    "> ");


            // Same result as above, just in a single line syntax
//        System.out.println("Welcome to Quizzard!\n1) Login\n2) Register\n3) Exit");

//        InputStreamReader inputReader = new InputStreamReader(System.in);
//        BufferedReader consoleReader = new BufferedReader(inputReader);

            // Preferred syntax, since we do not need an explicit reference to the InputStreamReader object.
            // It can simply be held by the BufferedReader reference.
//            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
            // The above line was commented out in order to move this reference up a scope so it could
            // be visible to all static methods without having to pass it around as an argument

//        String userSelection; // this declaration is uninitialized (not even null)
//        String userSelection = null; // What is null? Null is the lack of an object reference
            try {

                // We decided to move all the menu and user selection logic into this try block
                // because if an exception occurs when reading from the console, we do not want
                // to attempt to analyze the user's selection (since they couldn't make one)
                String userSelection = consoleReader.readLine();

                // Adding statements like the one below are examples of "breadcrumb statements"
                // These statements are not used as a part of the application's intended functionality
                // but instead are included during the development process as a way to help
                // developer debug the code.

                // Note: All breadcrumb statements should either be removed or replaced with log
                // statements that write them to a separate file
                System.out.println("User selected: " + userSelection);

                // quickScannerDemo();

                // Functionality: works
                // Readability: not so much
                // Maintainability: lacking
//            if (userSelection.equals("1")) {
//                System.out.println("The user selected Login");
//            } else if (userSelection.equals("2")) {
//                System.out.println("The user selected Register");
//            } else if (userSelection.equals("3")) {
//                System.out.println("The user selected Exit");
//            } else {
//                System.out.println("The user made an invalid selection");
//            }

                // Switch statement solution
                // Functionality: works
                // Readability: improved
                // Maintainability: improved
                switch (userSelection) {
                    case "1":
                        login();
                        break;
                    case "2":
                        register();
                        break;
                    case "3":
                        System.out.println("The user selected Exit");
//                        System.exit(0); // BAD! don't do it. Halts the JVM abruptly. Considered to be bad practice
                        isRunning = false;
                        break;
                    case "throw exception":
                        throw new RuntimeException(); // "throw" is used to explicitly throw an exception that will (hopefully) be handled elsewhere
                    default:
                        System.out.println("The user made an invalid selection");
                }

                // Causes an error at the moment
//                // Close our BufferedReader (or Scanner, if you're using that instead) so that our program will
//                // release control of the input source (could be a file, or the console, etc.)
//                consoleReader.close();

            } catch (IOException ioe) {
//            System.out.println(userSelection); // variables scope to the try block are not visible in related catch blocks

                // logic that handles the thrown exception is included in this catch block
                ioe.printStackTrace();
            }

            // Recursively calling main at the end of itself is known as recursion.
            // Recursion results in additional stackframes being placed into the stack (thereby taking up for memory)
            //        main(args);
        }

        // Close our BufferedReader (or Scanner, if you're using that instead) so that our program will
        // release control of the input source (could be a file, or the console, etc.)
        try {
            consoleReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // This method's signature indicates that it potentially throws an IOException
    // This allows for us to defer the catching/handling of this exception to the caller of this method.
    // This is called "exception propagation"
    public static void register() throws IOException {
        System.out.println("The user selected Register");
        // Things to obtain from user: first name, last name, email, username, and password
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

        System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s }\n", firstName, lastName);
    }

    public static void login() {
        System.out.println("The user selected Login");
    }

    public static void quickScannerDemo() {
        System.out.print("Scanner demo! Please provide a value: ");
        Scanner consoleScanner = new Scanner(System.in);
        String input = consoleScanner.next();
        System.out.println("User provided the following value to the Scanner: " + input);
    }

}
