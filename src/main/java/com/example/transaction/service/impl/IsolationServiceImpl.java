package com.example.transaction.service.impl;

import com.example.transaction.service.IsolationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

/*
 Service implementation applying different isolation levels using Spring's @Transactional annotation.
*/
@Service
public class IsolationServiceImpl implements IsolationService {

    /*
     Uses the database default isolation level.
    */
    @Override
    @Transactional(isolation = Isolation.DEFAULT)
    public void defaultIsolationExample() {

        System.out.println("DEFAULT isolation level executed");
    }

    /*
     Executes transaction using READ_COMMITTED isolation.
    */
    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void readCommittedExample() {

        System.out.println("READ_COMMITTED isolation level executed");
    }

    /*
     Executes transaction using REPEATABLE_READ isolation.
    */
    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void repeatableReadExample() {

        System.out.println("REPEATABLE_READ isolation level executed");
    }

    /*
     Executes transaction using SERIALIZABLE isolation.
    */
    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void serializableExample() {

        System.out.println("SERIALIZABLE isolation level executed");
    }
}