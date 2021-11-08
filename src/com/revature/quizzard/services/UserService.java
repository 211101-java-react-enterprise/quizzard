package com.revature.quizzard.services;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;

import java.io.File;
import java.io.FileWriter;

public class UserService {

    private AppUserDAO userDAO = new AppUserDAO();

    public boolean registerNewUser(AppUser newUser) {

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        // TODO: write logic that verifies that the new user's username and email are not already taken

        userDAO.save(newUser);

        return true;

    }

    // TODO: Implement me!
    public AppUser authenticateUser(String username, String password) {
        // Calls findByUserNameAndPassword in AppUserDAO
        // Receives either a user record or an error
        // Have username and password from user input
        // Need from file

        // Just make sure we're not receiving garbage
        if (username == null || username.equals("") || password == null || password.equals("")) {
            throw new InvalidRequestException("Invalid credential values provided");
        }
        AppUser authenticatedUser = userDAO.findUserByUsernameAndPassword(username, password);
        if (authenticatedUser != null) {
            return authenticatedUser;
        }
        throw new RuntimeException("No account found with provided credentials");
    }

    public boolean isUserValid(AppUser user) {
        System.out.println(user);
        if (user == null) return false;
        if (user.getFirstName() == null || user.getFirstName().trim().equals("")) return false;
        if (user.getLastName() == null || user.getLastName().trim().equals("")) return false;
        if (user.getEmail() == null || user.getEmail().trim().equals("")) return false;
        if (user.getUsername() == null || user.getUsername().trim().equals("")) return false;
        return user.getPassword() != null && !user.getPassword().trim().equals("");
    }

}
