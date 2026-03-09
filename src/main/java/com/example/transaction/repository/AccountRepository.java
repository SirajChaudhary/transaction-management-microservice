package com.example.transaction.repository;

import com.example.transaction.entity.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/*
 Repository demonstrating pessimistic row-level locking.

 Pessimistic locking prevents other transactions from modifying the row until the lock is released.
*/

public interface AccountRepository extends JpaRepository<Account, Long> {

    /*
     PESSIMISTIC WRITE LOCK

     Applies a PESSIMISTIC_WRITE lock on the selected account row.

     While the transaction is active:
     - Other transactions cannot modify this row
     - Other write operations must wait for the lock to release

     Used in scenarios like:
     - Bank money transfer
     - Inventory deduction
     - Ticket booking
    */

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM Account a WHERE a.id = :id")
    Optional<Account> findAccountForUpdate(Long id);
}