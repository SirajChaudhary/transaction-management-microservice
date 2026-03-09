package com.example.transaction.service;

/*
 Service interface defining operations used to demonstrate different transaction propagation strategies.
*/

public interface TransactionPropagationService {

    void requiredExample();

    void requiresNewExample();

    void nestedExample();

    void supportsExample();

    void notSupportedExample();

    void mandatoryExample();

    void neverExample();
}