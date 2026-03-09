package com.example.transaction.exception;

/*
 Custom exception used when an entity is not found in the database.
*/

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}