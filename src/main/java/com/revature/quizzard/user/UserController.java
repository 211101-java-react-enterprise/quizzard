package com.revature.quizzard.user;

import com.revature.quizzard.common.exceptions.AuthenticationException;
import com.revature.quizzard.common.exceptions.AuthorizationException;
import com.revature.quizzard.user.dtos.requests.EditUserRequest;
import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
import com.revature.quizzard.user.dtos.responses.RegistrationSuccessResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/username")
    public ResponseEntity<Void> checkUsernameAvailability(@RequestParam String username) {
        return userService.isUsernameAvailable(username) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @GetMapping("/email")
    public ResponseEntity<Void> checkEmailAvailability(@RequestParam String email) {
        return userService.isEmailAvailable(email) ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json", produces = "application/json")
    public RegistrationSuccessResponse register(@RequestBody @Valid NewRegistrationRequest newRegistrationRequest) {
        return userService.registerNewUser(newRegistrationRequest);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(consumes = "application/json")
    public void updateUserInfo(@RequestBody EditUserRequest editUserRequest, HttpServletRequest req) {

        HttpSession session = req.getSession(false);
        if (session == null) {
            throw new AuthenticationException("No session found.");
        }

        AppUser requestingUser = (AppUser) session.getAttribute("authUser");
        AppUser.AccountType requesterRole = requestingUser.getAccountType();

        boolean isAdminOrDev = requesterRole.equals(AppUser.AccountType.ADMIN) || requesterRole.equals(AppUser.AccountType.DEV);
        boolean requesterEditSelf = requestingUser.getId().equals(editUserRequest.getId());

        if (!requesterEditSelf && !isAdminOrDev) {
            throw new AuthorizationException("Forbidden request made.");
        }

        userService.updateUser(editUserRequest);

    }

}
