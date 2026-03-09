package com.example.transaction.service.impl;

import com.example.transaction.entity.Account;
import com.example.transaction.entity.Customer;
import com.example.transaction.repository.AccountRepository;
import com.example.transaction.repository.CustomerRepository;
import com.example.transaction.service.LockingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/*
 Service implementation performing locking operations.

 Transactions manage database updates while locking mechanisms protect records from concurrent modifications.
*/

@Service
@RequiredArgsConstructor
public class LockingServiceImpl implements LockingService {

    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;

    /*
     Updates customer balance.

     Optimistic locking is enforced automatically through the
     @Version field defined in the Customer entity.
    */
    @Override
    @Transactional
    public void optimisticUpdate(Long customerId, BigDecimal amount) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow();

        customer.setAccountBalance(customer.getAccountBalance().add(amount));

        customerRepository.save(customer);
    }

    /*
     Performs a money transfer between two accounts.

     The source account is retrieved using a pessimistic write lock
     to prevent concurrent transactions from modifying the row until the transaction completes.
    */
    @Override
    @Transactional
    public void pessimisticTransfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        Account from = accountRepository.findAccountForUpdate(fromAccountId)
                .orElseThrow();

        Account to = accountRepository.findById(toAccountId)
                .orElseThrow();

        from.setBalance(from.getBalance().subtract(amount));
        to.setBalance(to.getBalance().add(amount));
    }
}