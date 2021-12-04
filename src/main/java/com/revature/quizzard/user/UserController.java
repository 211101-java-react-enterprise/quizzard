package com.revature.quizzard.user;

import com.revature.quizzard.user.dtos.requests.NewRegistrationRequest;
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
