package com.example.transaction.service;

import com.example.transaction.entity.Customer;

import java.math.BigDecimal;
import java.util.List;

/*
 Service interface defining customer related operations.
*/

public interface CustomerService {

    Customer createCustomer(String name, BigDecimal creditScore, BigDecimal balance);

    Customer getCustomer(Long id);

    List<Customer> findHighCreditCustomers(BigDecimal score);

    List<Customer> findRichCustomers(BigDecimal balance);
}