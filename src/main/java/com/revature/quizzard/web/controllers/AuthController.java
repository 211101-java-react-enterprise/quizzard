package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.LoginRequest;
import com.revature.quizzard.web.util.Handler;
import com.revature.quizzard.web.util.HttpMethod;
import com.revature.quizzard.web.util.RequestMapping;

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

    @RequestMapping(value = "/quizzard/auth", method = HttpMethod.POST, consumes = "application/json")
    public void login(HttpServletRequest req, HttpServletResponse resp) {
        try {
            LoginRequest creds = mapper.readValue(req.getInputStream(), LoginRequest.class);
            AppUser authUser = userService.authenticateUser(creds.getUsername(), creds.getPassword());
            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("authUser", authUser);
            resp.setStatus(204);
        } catch (InvalidRequestException | UnrecognizedPropertyException e) {
            resp.setStatus(400);
        } catch (AuthenticationException e) {
            resp.setStatus(401);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }

    @RequestMapping(value = "/quizzard/auth", method = HttpMethod.DELETE)
    public void logout(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        resp.setStatus(204);
    }

}
