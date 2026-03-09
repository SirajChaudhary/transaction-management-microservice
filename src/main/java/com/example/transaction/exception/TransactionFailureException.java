package com.example.transaction.exception;

/*
 Custom exception representing transaction failures.
*/

public class TransactionFailureException extends RuntimeException {

    public TransactionFailureException(String message) {
        super(message);
    }
}