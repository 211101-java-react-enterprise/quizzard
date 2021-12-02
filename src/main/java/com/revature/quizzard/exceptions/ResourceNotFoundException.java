package com.revature.quizzard.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("No resources found using the provided search criteria");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
