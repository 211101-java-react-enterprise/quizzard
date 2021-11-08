package com.revature.quizzard.services;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;

public class VerifyService {
    private AppUserDAO userDAO = new AppUserDAO();

    public boolean registerNewUser(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        // TODO: write logic that verifies that the new user's username and email are not already taken

        userDAO.save(newUser);

        return true;

    }

    public AppUser authenticateUser(String username, String password) {
        //logic to verify user here
        //TODO: read file with fileBuffer, take in index 4 and 5 (username, password), verify if user is valid
        //return boolean value to let login screen if the login is successful

        return null;
    }

    public boolean isUserValid(AppUser user) {
        System.out.println(user);
        String correct_username = user.getUsername();
        String correct_password = user.getPassword();
    }
}
