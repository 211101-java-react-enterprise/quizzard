package com.revature.quizzard.user;

import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


// TODO Refactor to include Spring Bean validators

@Service
public class UserService {

    private final UserRepository userDAO ;

    public UserService(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public boolean registerNewUser(NewRegistrationRequest newRegistration) {

        AppUser newUser = new AppUser();
        newUser.setFirstName(newRegistration.getFirstName());
        newUser.setLastName(newRegistration.getLastName());
        newUser.setEmail(newRegistration.getEmail());
        newUser.setUsername(newRegistration.getUsername());
        newUser.setPassword(newRegistration.getPassword());

        if (!isUserValid(newUser)) {
            throw new InvalidRequestException("Invalid user data provided!");
        }

        boolean usernameAvailable = userDAO.findUserByUsername(newUser.getUsername()) == null;
        boolean emailAvailable = userDAO.findUserByEmail(newUser.getEmail()) == null;

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users:";
            if (!usernameAvailable) msg = msg + "\n\t- username";
            if (!emailAvailable) msg = msg + "\n\t- email";
            throw new ResourcePersistenceException(msg);
        }

        newUser.setId(UUID.randomUUID().toString());
        newUser.setAccountType(AppUser.AccountType.BASIC);
        AppUser registeredUser = userDAO.save(newUser);

        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }


        return true;

    }

    @Transactional(readOnly = true)
    public AppUser authenticateUser(String username, String password) {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        AppUser authenticatedUser = userDAO.findUserByUsernameAndPassword(username, password);

        if (authenticatedUser == null) {
            throw new AuthenticationException();
        }

        return authenticatedUser;
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
