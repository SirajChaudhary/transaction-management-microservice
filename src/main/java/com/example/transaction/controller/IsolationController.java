package com.example.transaction.controller;

import com.example.transaction.service.IsolationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 Controller exposing APIs to demonstrate transaction isolation levels.

 What is Transaction Isolation?

 Isolation is one of the ACID properties of database transactions.
 It defines how and when changes made by one transaction become visible to other concurrent transactions.

 Standard SQL defines four isolation levels:

 - READ_UNCOMMITTED
 - READ_COMMITTED
 - REPEATABLE_READ
 - SERIALIZABLE

 Spring also provides:

 - DEFAULT

 DEFAULT means the transaction will use the default isolation level configured by the underlying database.

 However, some databases (such as PostgreSQL) do not support READ_UNCOMMITTED and internally treat it as READ_COMMITTED.

 In systems where multiple transactions run at the same time, isolation levels prevent issues such as:
 - Dirty Reads: Reading uncommitted data written by another transaction.
 - Non-repeatable Reads: Reading the same row multiple times and getting different values.
 - Phantom Reads: Executing the same query twice and getting different result rows.
 - Lost Updates: Two transactions update the same row and one update overwrites the other.
*/

@RestController
@RequestMapping("/api/v1/isolation")
@RequiredArgsConstructor
public class IsolationController {

    private final IsolationService isolationService;

    /*
     DEFAULT

     Uses the default isolation level configured by the database.

     Behavior depends on the database configuration.
     For PostgreSQL the default is READ_COMMITTED.
    */
    @PostMapping("/default")
    public String defaultIsolation() {

        isolationService.defaultIsolationExample();
        return "DEFAULT isolation executed";
    }

    /*
     READ_COMMITTED

     A transaction can only read data that has already been committed by other transactions.

     Prevents:
     - Dirty reads

     But still allows:
     - Non-repeatable reads
     - Phantom reads
     - Lost updates
    */
    @PostMapping("/read-committed")
    public String readCommitted() {

        isolationService.readCommittedExample();
        return "READ_COMMITTED executed";
    }

    /*
     REPEATABLE_READ

     Ensures that rows read during a transaction cannot change until the transaction completes.

     Prevents:
     - Dirty reads
     - Non-repeatable reads
     - Lost updates (in most databases)

     But may still allow:
     - Phantom reads (depending on database implementation)
    */
    @PostMapping("/repeatable-read")
    public String repeatableRead() {

        isolationService.repeatableReadExample();
        return "REPEATABLE_READ executed";
    }

    /*
     SERIALIZABLE

     The strictest isolation level.

     Transactions behave as if they run sequentially, completely isolated from each other.

     Prevents:
     - Dirty reads
     - Non-repeatable reads
     - Phantom reads
     - Lost updates

     This provides maximum consistency but may reduce performance because transactions may block each other more frequently.
    */
    @PostMapping("/serializable")
    public String serializable() {

        isolationService.serializableExample();
        return "SERIALIZABLE executed";
    }
}