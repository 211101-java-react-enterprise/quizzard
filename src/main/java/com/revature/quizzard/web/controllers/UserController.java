package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.ErrorResponse;
import com.revature.quizzard.web.dtos.NewRegistration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "application/json")
    public void register(NewRegistration newRegistrationRequest) {
        userService.registerNewUser(newRegistrationRequest);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({InvalidRequestException.class, UnrecognizedPropertyException.class})
    public ErrorResponse handleBadRequests(Exception e) {
        return new ErrorResponse(400, e);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleOtherExceptions(Exception e) {
        e.printStackTrace();
        return new ErrorResponse(500, "An internal server exception occurred. Please check the server logs for more info.", e);
    }

}
