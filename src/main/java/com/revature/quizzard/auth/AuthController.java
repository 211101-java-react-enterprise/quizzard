package com.revature.quizzard.auth;

import com.revature.quizzard.auth.dtos.responses.PrincipalResponse;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.user.UserService;
import com.revature.quizzard.auth.dtos.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    public PrincipalResponse authenticate(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        AppUser authUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        httpSession.setAttribute("authUser", authUser);
        return new PrincipalResponse(authUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }

}
