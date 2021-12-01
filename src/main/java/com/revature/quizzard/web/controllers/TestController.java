package com.revature.quizzard.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tester")
public class TestController {

    @GetMapping("/test")
    public @ResponseBody String test(@RequestParam String someValue) {
        return someValue;
        //http://localhost:8080/quizzard/tester/test/?someValue=ddfsd
    }

}
