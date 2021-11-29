package com.revature.quizzard.web.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.revature.quizzard.exceptions.AuthenticationException;
import com.revature.quizzard.exceptions.InvalidRequestException;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import com.revature.quizzard.web.dtos.Credentials;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class AuthServlet extends HttpServlet {

    private final UserService userService;
    private final ObjectMapper mapper;

    public AuthServlet(UserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Credentials creds = mapper.readValue(req.getInputStream(), Credentials.class);
            AppUser authUser = userService.authenticateUser(creds.getUsername(), creds.getPassword());

            // adds a Cookie to the response that is stored within Tomcat
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

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate(); // invalidates the session associated with this request (logging the user out)
        }
    }

}
