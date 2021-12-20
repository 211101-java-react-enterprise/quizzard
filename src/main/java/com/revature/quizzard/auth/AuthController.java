package com.revature.quizzard.auth;

import com.revature.quizzard.auth.dtos.responses.Principal;
import com.revature.quizzard.user.AppUser;
import com.revature.quizzard.user.UserService;
import com.revature.quizzard.auth.dtos.requests.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final TokenService tokenService;

    @Autowired
    public AuthController(UserService userService, TokenService tokenService) {
        this.userService = userService;
        this.tokenService = tokenService;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public Principal authenticate(@RequestBody LoginRequest loginRequest, HttpServletResponse resp) {
        AppUser authUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        Principal payload = new Principal(authUser);
        String token = tokenService.generateToken(payload);
        resp.setHeader("Authorization", token);
        return payload;
    }

}
