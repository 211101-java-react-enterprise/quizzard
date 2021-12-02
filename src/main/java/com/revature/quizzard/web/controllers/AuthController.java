package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.ErrorResponse;
import com.revature.quizzard.web.dtos.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void authenticate(@RequestBody LoginRequest loginRequest, HttpSession httpSession) {
        AppUser authUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());
        httpSession.setAttribute("authUser", authUser);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void invalidateSession(HttpSession session) {
        session.invalidate();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestException.class, UnrecognizedPropertyException.class})
    public ErrorResponse handleBadRequests(Exception e) {
        return new ErrorResponse(400, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ErrorResponse handleAuthenticationException(AuthenticationException e) {
        return new ErrorResponse(401, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherExceptions(Exception e) {
        e.printStackTrace();
        return new ErrorResponse(500, "An internal server exception occurred. Please check the server logs for more info.", e);
    }

}
