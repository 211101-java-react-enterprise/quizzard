package com.revature.quizzard.web.controllers;

import com.revature.quizzard.web.util.Handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController implements Handler {

    public String test(HttpServletRequest req, HttpServletResponse resp) {
        return "/test works!";
    }
}
