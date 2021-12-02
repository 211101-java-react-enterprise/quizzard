package com.revature.quizzard.web.controllers;

import com.revature.quizzard.web.util.Handler;
import com.revature.quizzard.web.util.HttpMethod;
import com.revature.quizzard.web.util.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestController implements Handler {

    @RequestMapping(value = "/quizzard/test", method = HttpMethod.GET, produces = "text/plain")
    public String test(HttpServletRequest req, HttpServletResponse resp) {
        return "/test works!";
    }

}
