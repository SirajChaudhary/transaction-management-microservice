package com.example.transaction.service;

import com.example.transaction.entity.Loan;

import java.math.BigDecimal;

/*
 Service interface defining loan processing operations.
*/

public interface LoanService {

    Loan applyLoan(Long customerId, BigDecimal amount);
}