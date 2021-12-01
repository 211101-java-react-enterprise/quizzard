package com.revature.quizzard.web.controllers;

import com.revature.quizzard.daos.AppUserDAO;
import com.revature.quizzard.models.AppUser;
import com.revature.quizzard.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/auther")
public class AuthController {

    @GetMapping("/login")
    public @ResponseBody String auth(@RequestParam String user, String pass){
        AppUserDAO userDAO = new AppUserDAO();
        UserService userService = new UserService(userDAO);

        AppUser authUser = userService.authenticateUser(user, pass);

//        HttpSession httpSession = req.getSession();
//        httpSession.setAttribute("authUser", authUser);

        if (authUser != null) {return "Yes, you're in.";}
        return "no";

//        return user + " " + pass;
    }
}
