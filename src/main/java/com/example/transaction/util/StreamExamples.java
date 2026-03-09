package com.example.transaction.util;

import com.example.transaction.entity.Customer;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/*
 Demonstrates Java Stream API and functional programming.
*/

public class StreamExamples {

    /*
     Filters customers with high balance using Java Stream API.
    */

    public static List<Customer> filterRichCustomers(List<Customer> customers) {

        return customers.stream()
                .filter(c -> c.getAccountBalance()
                        .compareTo(new BigDecimal("100000")) > 0)
                .collect(Collectors.toList());
    }
}