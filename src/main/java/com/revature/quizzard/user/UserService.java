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

@Service
public class UserService {

    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    public RegistrationSuccessResponse registerNewUser(NewRegistrationRequest newRegistration) {

        AppUser newUser = new AppUser(); // entity state: transient (not associated with an active session)
        newUser.setFirstName(newRegistration.getFirstName());
        newUser.setLastName(newRegistration.getLastName());
        newUser.setEmail(newRegistration.getEmail());
        newUser.setUsername(newRegistration.getUsername());
        newUser.setPassword(newRegistration.getPassword());

        boolean usernameAvailable = !userRepo.findAppUserByUsername(newUser.getUsername()).isPresent();
        boolean emailAvailable = !userRepo.findAppUserByEmail(newUser.getEmail()).isPresent();

        if (!usernameAvailable || !emailAvailable) {
            String msg = "The values provided for the following fields are already taken by other users:";
            if (!usernameAvailable) msg = msg + "\n\t- username";
            if (!emailAvailable) msg = msg + "\n\t- email";
            throw new ResourcePersistenceException(msg);
        }

        newUser.setId(UUID.randomUUID().toString());
        newUser.setAccountType(AppUser.AccountType.BASIC);
        userRepo.save(newUser); // entity state: persistent (associated with an active session)
        return new RegistrationSuccessResponse(newUser.getId());

    }

    @Transactional(readOnly = true)
    public AppUser authenticateUser(String username, String password) {

        if (username == null || username.trim().equals("") || password == null || password.trim().equals("")) {
            throw new InvalidRequestException("Invalid credential values provided!");
        }

        return userRepo.findUserByUsernameAndPassword(username, password)
                       .orElseThrow(AuthenticationException::new);

    }

    @Transactional
    public void updateUser(EditUserRequest editRequest) {

        try {
            AppUser original = userRepo.findById(editRequest.getId())
                                       .orElseThrow(ResourceNotFoundException::new);

            Predicate<String> notNullOrEmpty = str -> str != null && !str.equals("");

            if (notNullOrEmpty.test(editRequest.getFirstName())) {
                original.setFirstName(editRequest.getFirstName());
            } else if (notNullOrEmpty.test(editRequest.getLastName())) {
                original.setLastName(editRequest.getLastName());
            } else if (notNullOrEmpty.test(editRequest.getEmail())) {
                if (userRepo.findAppUserByEmail(editRequest.getEmail()).isPresent()) {
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
            return !userRepo.findAppUserByUsername(username).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Transactional
    public boolean isEmailAvailable(String email) {
        try {
            return !userRepo.findAppUserByEmail(email).isPresent();
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
