package com.revature.quizzard.common.exceptions;

public class AuthenticationException extends RuntimeException {

    public AuthenticationException(String s) {
        super(s);
    }

    public AuthenticationException() {
        super("Incorrect credentials provided. Could not authenticate.");
    }

}
