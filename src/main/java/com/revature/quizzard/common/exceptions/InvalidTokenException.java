package com.revature.quizzard.common.exceptions;

public class InvalidTokenException extends RuntimeException {

    public InvalidTokenException() {
        super("Invalid token provided! Could not parse claims!");
    }

    public InvalidTokenException(String message) {
        super(message);
    }

}
