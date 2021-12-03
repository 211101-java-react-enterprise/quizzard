package com.revature.quizzard.web.controllers;

import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.NewRegistrationRequest;
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
    public void register(@RequestBody NewRegistrationRequest newRegistrationRequest) {
        userService.registerNewUser(newRegistrationRequest);
    }

}
