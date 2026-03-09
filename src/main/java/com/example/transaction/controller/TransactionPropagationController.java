package com.example.transaction.controller;

import com.example.transaction.service.TransactionPropagationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 Controller exposing APIs to demonstrate transaction propagation.

 What is Transaction Propagation?

 Transaction propagation defines how a transaction behaves when a transactional method calls another transactional method.

 It determines whether the called method should:
 - Join an existing transaction
 - Start a new independent transaction
 - Execute inside a nested transaction
 - Execute without a transaction

 Spring supports the following 7 propagation strategies:

 REQUIRED (Most commonly used – default)
 - Joins the current transaction if one exists.
 - If no transaction exists, a new transaction is created.
 - Example: Order creation calling payment processing.

 REQUIRES_NEW (Commonly used for independent operations)
 - Always starts a new independent transaction.
 - Suspends any existing transaction until this one completes.
 - Example: Writing audit logs even if the main transaction fails.

 NESTED (Used when partial rollback is required)
 - Executes inside a nested transaction using database savepoints.
 - The inner transaction can roll back without affecting the outer transaction.
 - Example: Processing multiple order items where failure of one item
   should not cancel the entire order.

 SUPPORTS (Used for optional transactional operations)
 - Executes within the current transaction if one exists.
 - If no transaction exists, it runs without a transaction.
 - Example: Read-only logging or optional reporting.

 NOT_SUPPORTED (Used for non-transactional operations)
 - Always executes without a transaction.
 - If a transaction exists, it is suspended temporarily.
 - Example: Long-running reporting queries that should not hold transactions.

 MANDATORY (Rare – used for strict transaction requirements)
 - Must run inside an existing transaction.
 - If no transaction exists, an exception is thrown.
 - Example: Internal service methods that must always be executed
   within a business transaction.

 NEVER (Rare – used when transactions must not exist)
 - Must execute without a transaction.
 - If a transaction exists, an exception is thrown.
 - Example: Monitoring or system operations that should never
   participate in transactions.

 Transaction propagation is controlled using the @Transactional annotation.
*/

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
public class TransactionPropagationController {

    private final TransactionPropagationService propagationService;

    /*
     Demonstrates REQUIRED propagation.
    */
    @PostMapping("/required")
    public String requiredExample() {

        propagationService.requiredExample();
        return "REQUIRED transaction executed";
    }

    /*
     Demonstrates REQUIRES_NEW propagation.
    */
    @PostMapping("/requires-new")
    public String requiresNewExample() {

        propagationService.requiresNewExample();
        return "REQUIRES_NEW transaction executed";
    }

    /*
     Demonstrates NESTED propagation.
    */
    @PostMapping("/nested")
    public String nestedExample() {

        propagationService.nestedExample();
        return "NESTED transaction executed";
    }

    /*
     Demonstrates SUPPORTS propagation.
    */
    @PostMapping("/supports")
    public String supportsExample() {

        propagationService.supportsExample();
        return "SUPPORTS transaction executed";
    }

    /*
     Demonstrates NOT_SUPPORTED propagation.
    */
    @PostMapping("/not-supported")
    public String notSupportedExample() {

        propagationService.notSupportedExample();
        return "NOT_SUPPORTED transaction executed";
    }

    /*
     Demonstrates MANDATORY propagation.
    */
    @PostMapping("/mandatory")
    public String mandatoryExample() {

        propagationService.mandatoryExample();
        return "MANDATORY transaction executed";
    }

    /*
     Demonstrates NEVER propagation.
    */
    @PostMapping("/never")
    public String neverExample() {

        propagationService.neverExample();
        return "NEVER transaction executed";
    }
}