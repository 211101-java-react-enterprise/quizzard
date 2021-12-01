package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.LoginRequest;
import com.revature.quizzard.web.util.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthController implements Handler {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthController(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    public void login(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LoginRequest creds = mapper.readValue(req.getInputStream(), LoginRequest.class);
            AppUser authUser = userService.authenticateUser(creds.getUsername(), creds.getPassword());

            // adds a Cookie to the response containing a SESSION_ID
            // that SESSION_ID is stored within Tomcat
            // and is used to identify the requester in future requests
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("authUser", authUser);


            resp.setStatus(204); // success, but nothing to return (NO_CONTENT)
        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400); // user made a bad request
        } catch (AuthenticationException e) {
            resp.setStatus(401); // user provided incorrect credentials
        } catch (Exception e) {
            e.printStackTrace(); // for dev purposes only, to be deleted before push to prod
            resp.setStatus(500);
        }
    }

    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
}
