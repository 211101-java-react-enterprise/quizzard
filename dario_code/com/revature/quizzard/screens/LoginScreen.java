package com.revature.quizzard.screens;

public class LoginScreen extends Screen {

    private final UserService userService;

    public LoginScreen(BufferedReader consoleReader, ScreenRouter router, UserService userService,) {
        super("LoginScreen", "/login", consoleReader, router);
        this.userService = userService;
    }

    @Override
    public void render() throws Exception {
        System.out.println("The user selected Login");

        System.out.println("Please provide us with your login information.");

        System.out.print("Username:");
        String firstName = consoleReader.readLine();

        System.out.print("Password:");
        String lastName = consoleReader.readLine();





}
