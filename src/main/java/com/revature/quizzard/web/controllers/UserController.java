package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.util.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class UserController implements Handler {

    private final UserService userService;
    private final ObjectMapper mapper;

    public UserController(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public void registerNewUser(HttpServletRequest req, HttpServletResponse resp) {

        try {
            AppUser newUser = mapper.readValue(req.getInputStream(), AppUser.class);
            boolean wasRegistered = userService.registerNewUser(newUser);
            if (wasRegistered) {
                System.out.println("User successfully persisted!");
                resp.setStatus(201);
            } else {
                System.out.println("Could not persist user! Check logs.");
                resp.setStatus(500); // server error
            }
        } catch (IOException e) {
            resp.setStatus(400); // client error; BAD REQUEST
            e.printStackTrace();
        }
    }

}
