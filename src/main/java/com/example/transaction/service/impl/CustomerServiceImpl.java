package com.example.transaction.service.impl;

import com.example.transaction.entity.Customer;
import com.example.transaction.exception.ResourceNotFoundException;
import com.example.transaction.repository.CustomerRepository;
import com.example.transaction.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/*
 Service implementation responsible for Customer operations.

 It also demonstrates
 - Builder pattern
*/

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(String name, BigDecimal creditScore, BigDecimal balance) {

        // Builder pattern for creating entity
        Customer customer = Customer.builder()
                .name(name)
                .creditScore(creditScore)
                .accountBalance(balance)
                .build();

        return customerRepository.save(customer);
    }

    @Override
    public Customer getCustomer(Long id) {

        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Override
    public List<Customer> findHighCreditCustomers(BigDecimal score) {

        // Uses JPQL query defined in repository
        return customerRepository.findCustomersWithHighCredit(score);
    }

    @Override
    public List<Customer> findRichCustomers(BigDecimal balance) {

        // Uses native SQL query
        return customerRepository.findRichCustomers(balance);
    }
}