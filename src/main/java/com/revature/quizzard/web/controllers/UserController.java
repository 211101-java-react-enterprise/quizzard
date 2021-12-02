package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.util.Handler;
import com.revature.quizzard.web.util.HttpMethod;
import com.revature.quizzard.web.util.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserController implements Handler {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserController(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @RequestMapping(value = "/quizzard/users", method = HttpMethod.POST, consumes = "application/json")
    public void registerNewUser(HttpServletRequest req, HttpServletResponse resp) {
        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            userService.registerNewUser(newUser);
            resp.setStatus(201);
        } catch (Exception e) {
            resp.setStatus(500);
            e.printStackTrace();
        }
    }

}
