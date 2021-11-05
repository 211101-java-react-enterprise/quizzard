package com.revature.quizzard.services;

import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.util.AppState;

public class UserService {
    public boolean registerNewUser(AppUser newUser) {
        if (isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid Request!")
        }
    }

    public AppUser authenticateUser() {

    }

    public boolean isUserValid(AppUser user) {
        if (newUser == null) return false;
        if (newUser.getFirstName() == null || newUser.getFirstName().trim().equals("")) return false;
        if (newUser.getLastName() == null || newUser.getLastName().trim().equals("")) return false;
        if (newUser.getEmail() == null || newUser.getFirstName().trim().equals("")) return false;
        if (newUser.getUsername() == null || newUser.getFirstName().trim().equals("")) return false;
        if (newUser.getPassword() == null || newUser.getFirstName().trim().equals("")) return false;

    }
}
