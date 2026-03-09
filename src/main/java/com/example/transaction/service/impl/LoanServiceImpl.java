package com.example.transaction.service.impl;

import com.example.transaction.entity.Customer;
import com.example.transaction.entity.Loan;
import com.example.transaction.repository.CustomerRepository;
import com.example.transaction.repository.LoanRepository;
import com.example.transaction.service.AuditService;
import com.example.transaction.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/*
 Service implementation responsible for loan processing.

 Demonstrates ACID transactional behavior using Spring's @Transactional annotation.

 The loan approval workflow includes:
 - validating the customer
 - creating the loan record
 - writing an audit log

 If any operation fails → the transaction is rolled back and all database changes are reverted.
*/

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final AuditService auditService;

    @Override
    @Transactional
    public Loan applyLoan(Long customerId, BigDecimal amount) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        if (customer.getCreditScore().compareTo(new BigDecimal("600")) < 0) {

            auditService.logAction("LOAN_REJECTED", "FAILED");

            throw new RuntimeException("Credit score too low");
        }

        Loan loan = Loan.builder()
                .customer(customer)
                .amount(amount)
                .status("APPROVED")
                .build();

        Loan savedLoan = loanRepository.save(loan);

        auditService.logAction("LOAN_APPROVED", "SUCCESS");

        return savedLoan;
    }
}