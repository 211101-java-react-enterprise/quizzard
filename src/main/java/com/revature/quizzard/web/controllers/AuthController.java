package com.revature.quizzard.web.controllers;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.LoginRequest;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;

@Controller
@RequestMapping("/auth")
public class AuthController {
    UserService userService = new UserService(new AppUserDAO());

    @PostMapping(value="/login",consumes = "application/json")
    public @ResponseBody void login(@RequestBody LoginRequest loginRequest, HttpServletResponse resp , HttpServletRequest req) {


        try {
            AppUser authUser = userService.authenticateUser(loginRequest.getUsername(), loginRequest.getPassword());

            HttpSession httpSession = req.getSession();
            httpSession.setAttribute("authUser",authUser);
            resp.setStatus(204);
        }catch (InvalidRequestException e) {
            resp.setStatus(400); // user made a bad request
        } catch (AuthenticationException e) {
            resp.setStatus(401); // user provided incorrect credentials
        } catch (Exception e) {
            e.printStackTrace(); // for dev purposes only, to be deleted before push to prod
            resp.setStatus(500);
        }
    }

    @DeleteMapping(value="/logout")
    public @ResponseBody void logout(HttpServletResponse resp , HttpServletRequest req){
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }


}
