package com.example.transaction.service;

import java.math.BigDecimal;

/*
 Service interface defining operations that demonstrate database locking strategies.
*/

public interface LockingService {

    void optimisticUpdate(Long customerId, BigDecimal amount);

    void pessimisticTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount);
}