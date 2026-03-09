package com.example.transaction.controller;

import com.example.transaction.entity.Customer;
import com.example.transaction.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/*
 Controller exposing Customer APIs.
*/

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Customer> createCustomer(
            @RequestParam String name,
            @RequestParam BigDecimal creditScore,
            @RequestParam BigDecimal balance) {

        return ResponseEntity.ok(
                customerService.createCustomer(name, creditScore, balance)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable Long id) {

        return ResponseEntity.ok(
                customerService.getCustomer(id)
        );
    }

    /*
     Demonstrates JPQL query
    */
    @GetMapping("/high-credit")
    public ResponseEntity<List<Customer>> highCreditCustomers(
            @RequestParam BigDecimal score) {

        return ResponseEntity.ok(
                customerService.findHighCreditCustomers(score)
        );
    }

    /*
     Demonstrates native SQL query
    */
    @GetMapping("/rich")
    public ResponseEntity<List<Customer>> richCustomers(
            @RequestParam BigDecimal balance) {

        return ResponseEntity.ok(
                customerService.findRichCustomers(balance)
        );
    }
}