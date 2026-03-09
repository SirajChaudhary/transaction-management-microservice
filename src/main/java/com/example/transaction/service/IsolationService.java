package com.example.transaction.service;

/*
 Service interface for executing transactions using different isolation levels.

 DEFAULT
 READ_COMMITTED
 REPEATABLE_READ
 SERIALIZABLE
*/

public interface IsolationService {

    void defaultIsolationExample();

    void readCommittedExample();

    void repeatableReadExample();

    void serializableExample();
}