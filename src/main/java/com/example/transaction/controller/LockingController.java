package com.example.transaction.controller;

import com.example.transaction.service.LockingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/*
 Controller exposing APIs to demonstrate database locking strategies.

 In concurrent systems multiple transactions may attempt to modify the same database record at the same time.
 Locking mechanisms help maintain data consistency and prevent conflicting updates.

 This microservice demonstrates two common locking strategies:

 OPTIMISTIC LOCKING
 - Assumes conflicts are rare.
 - Uses a version column (@Version) to detect concurrent updates.
 - If another transaction modifies the record first,
   the current transaction fails with an optimistic lock exception.

 PESSIMISTIC LOCKING
 - Assumes conflicts are likely.
 - Locks the database row immediately.
 - Other transactions must wait until the lock is released.

 These strategies are widely used in systems such as:
 - banking transactions
 - inventory management
 - ticket booking systems
*/

@RestController
@RequestMapping("/api/v1/locking")
@RequiredArgsConstructor
public class LockingController {

    private final LockingService lockingService;

    /*
     Demonstrates optimistic locking.

     Example:
     Two transactions update the same customer balance.
     The version field ensures that only one update succeeds.
    */
    @PostMapping("/optimistic")
    public String optimisticLocking(
            @RequestParam Long customerId,
            @RequestParam BigDecimal amount) {

        lockingService.optimisticUpdate(customerId, amount);
        return "Optimistic update executed";
    }

    /*
     Demonstrates pessimistic locking.

     Example scenario:
     Bank account transfer where the source account must be locked to prevent concurrent withdrawals.
    */
    @PostMapping("/pessimistic-transfer")
    public String pessimisticTransfer(
            @RequestParam Long fromAccountId,
            @RequestParam Long toAccountId,
            @RequestParam BigDecimal amount) {

        lockingService.pessimisticTransfer(fromAccountId, toAccountId, amount);
        return "Pessimistic transfer executed";
    }
}