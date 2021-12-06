package com.revature.quizzard.user;

import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.InvalidRequestException;
import com.revature.quizzard.common.exceptions.ResourceNotFoundException;
import com.revature.quizzard.common.exceptions.ResourcePersistenceException;
import com.revature.quizzard.user.dtos.requests.EditUserRequest;
import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
import com.revature.quizzard.user.dtos.responses.RegistrationSuccessResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.function.Predicate;

// TODO Refactor to include Spring Bean validators

@Service
public class UserService {

    private final UserRepository userDAO ;

    public UserService(UserRepository userDAO) {
        this.userDAO = userDAO;
    }

    @Transactional
    public RegistrationSuccessResponse registerNewUser(NewRegistrationRequest newRegistration) {

        AppUser newUser = new AppUser(); // entity state: transient (not associated with an active session)
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
        AppUser registeredUser = userDAO.save(newUser); // entity state: persistent (associated with an active session)

        if (registeredUser == null) {
            throw new ResourcePersistenceException("The user could not be persisted to the datasource!");
        }


        return new RegistrationSuccessResponse(registeredUser.getId());

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

    @Transactional
    public void updateUser(EditUserRequest editRequest) {

        try {
            AppUser original = userDAO.findById(editRequest.getId());

            if (original == null) {
                throw new ResourceNotFoundException();
            }

            Predicate<String> notNullOrEmpty = str -> str != null && !str.equals("");

            if (notNullOrEmpty.test(editRequest.getFirstName())) {
                original.setFirstName(editRequest.getFirstName());
            } else if (notNullOrEmpty.test(editRequest.getLastName())) {
                original.setLastName(editRequest.getLastName());
            } else if (notNullOrEmpty.test(editRequest.getEmail())) {
                if (userDAO.findUserByEmail(editRequest.getEmail()) != null) {
                    throw new ResourcePersistenceException("The provided email is already by another user.");
                }
                original.setEmail(editRequest.getEmail());
            } else if (notNullOrEmpty.test(editRequest.getPassword())) {
                original.setPassword(editRequest.getPassword());
            }

        } catch (ResourcePersistenceException e) {
            throw e;
        } catch (Exception e) {
            throw new ResourcePersistenceException("Could not update user due to nested exception", e);
        }

    }

    @Transactional
    public boolean isUsernameAvailable(String username) {
        try {
            return userDAO.findUserByUsername(username) == null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public boolean isEmailAvailable(String email) {
        try {
            return userDAO.findUserByEmail(email) == null;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
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
