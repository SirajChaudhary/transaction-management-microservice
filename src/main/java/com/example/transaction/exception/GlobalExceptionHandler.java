package com.example.transaction.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*
 Global exception handler using @RestControllerAdvice.

 This centralizes error handling across controllers.
*/

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {

        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TransactionFailureException.class)
    public ResponseEntity<String> handleTransaction(TransactionFailureException ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneric(Exception ex) {

        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}