package com.revature.quizzard.services;

import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

//*************************************************

public class UserService {

    //-------------------------------------------------

    public boolean registerNewUser(AppUser newUser) {
        boolean isValid = true;

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        // TODO: write logic that verifies that the new user's username and email are not already taken
        // checking logic
        try {
            File usersFile = new File("resources/data.txt");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(usersFile));
            String rawData;

            while ((rawData = bufferedReader.readLine()) != null) {
                String[] rawDataArray = rawData.split(":");

                if (rawDataArray[2].equals(newUser.getEmail()) || rawDataArray[3].equals(newUser.getUsername())) {
                    bufferedReader.close();
                    throw new InvalidRequestException("Invalid, Either Username or Email are already in use");
                }

            }

            bufferedReader.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }


        // TODO: Find a better place for this logic (hint: DAO pattern)

        try {
            File usersFile = new File("resources/data.txt");

            // the true argument here allows for us to append to the file, rather than completely overwriting it
            FileWriter fileWriter = new FileWriter(usersFile, true);
            fileWriter.write(newUser.toFileString() + "\n");
            fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;

    }

    //-------------------------------------------------

    public AppUser authenticateUser(String username, String password) {
        return null;
    }

    //-------------------------------------------------

    public boolean isUserValid(AppUser user) {
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

    //-------------------------------------------------

}
