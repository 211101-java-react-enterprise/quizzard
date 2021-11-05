package com.revature.quizzard;

import com.revature.quizzard.models.AppUser;

import java.io.*;
import java.util.ArrayList;

public class QuizzardDriver {//ThisIsPascalCasing thisIsCamelCase
    static BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) {
    boolean isRunning = true;
        while(isRunning) {
        System.out.print("Welcome to Quizzard!\n" +
                "1) Login\n" +
                "2) Register\n" +
                "3) Exit\n"+
                "> ");

        //buffered reader vs. scanner. buffered reader is best for strings (everything is interpreted as string).
        //scanner is best for int/float/bool. Looking for specific values

        //InputStreamReader inputReader = new InputStreamReader(System.in);
        //BufferedReader consoleReader = new BufferedReader(inputReader);//need to create reader obj
        // is same as:
        //BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));//consoleReader is method scoped


//        String userSelection;//this is uninitialized (not null)
        //String userSelection = null; //null is lack of an object reference

            try {
                //We decided to move all the menu and user selection logic into this try block bc if an exception occurs when reading from the console, we do not
                //want to attempt to analyze the user's selection (since they couldn't make one)
                String userSelection = consoleReader.readLine();//need logic that handles error if file is corrupted. This is a checked exception. used try catch block

                //Adding statements like the one below are examples of "breadcrumb statements"
                //these statements are not used as a part of the applications intended functionality
                //but instead are included during the develpment process as a  way to help develpsers debug the code

                //Note: all breadcrumb statements should either be removed or replaced with log:
                //statements that write them to seperate file.
                //System.out.println("user selected: "+userSelection); //doesn't work (scoping issues) if userSelection is declared outside of try block.

                //functionality: works
                //readability: not so much
                //maintainablity: lacking
//            if (userSelection.equals("1")){
//                System.out.println("The user selected login");
//            }
//            else if (userSelection.equals("2")){
//                System.out.println("The user selected register");
//            }
//            else if (userSelection.equals("3")){
//                System.out.println("The user selected exit");
//            }


                //switch option:
                switch (userSelection) {
                    case "1":
                        login();
                        break;

                    case "2":
                        register();
                        break;
                    case "3":
                        System.out.println("The user selected exit");
                        //System.exit(0) halts the JVM abruptly
                        isRunning = false;
                        break;
                    case "throw exception":
                        throw new RuntimeException();
                    default:
                        System.out.println("the user made an invalid selection");
                }

            } catch (IOException ioe) {
                //logic that handles the thrown exception is included in this catch block
                ioe.printStackTrace();
            }

            //recursively calling main at the end of its suelf is known as recursion
            //recursion results in additional stackframes being placed into the stack (thereby taking up for memory)
            //main(args);
        }
    }

    //throw is used to explicitly throw an exception that will hopefully be handled elsewhere
    //can also take BufferedReader consoleReader as an argument instead of in class
    public static void register() throws IOException{
        System.out.println("The user selected register");
        System.out.println("Please provide us with some basic information:");
        System.out.print("First Name: ");
        String firstName = consoleReader.readLine();
        System.out.print("Last Name: ");
        String lastName = consoleReader.readLine();
        System.out.print("username: ");
        String username = consoleReader.readLine();
        System.out.print("password: ");
        String password = consoleReader.readLine();
        System.out.print("email: ");
        String email = consoleReader.readLine();

        //System.out.println("Provided user first and last name: { \"firstName\": " + firstName + "\", lastName\": " + lastName + " }\n");
        // System.out.printf("Provided user first and last name: { \"firstName\": %s, \"lastName\": %s, }\n", firstName, lastName);

        AppUser newUser = new AppUser(firstName, lastName, email, username, password);
        System.out.println("newly created user: "+newUser);

        File usersFile = new File("resources/data.txt");
        FileWriter fileWriter = new FileWriter(usersFile, true);
        fileWriter.write(newUser.toString());
        fileWriter.close();
// String format specifiers: %s (strings), %d (whole numbers), %f (decimal values)

        String someData = "cole:paris:cole.paris@revature.net:cole_paris:happyboii:";
        String[] getFragments = someData.split(":");

        //System.out.printf("provided user info: { \"username\": %s", username);

    }
    public static void login(){
        System.out.println("The user selected login");
    }

    public static void genericExampele(){
        ArrayList<String> myStringArrayList = new ArrayList<>();
        myStringArrayList.add("hello!");
    }
}
