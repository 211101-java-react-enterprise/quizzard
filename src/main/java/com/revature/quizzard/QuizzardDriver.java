package com.revature.quizzard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // implies: @Configuration, @AutoConfiguration (does the @Enable___ for us), @ComponentScan
public class QuizzardDriver {

    public static void main(String[] args) {
        SpringApplication.run(QuizzardDriver.class, args);
    }

}
