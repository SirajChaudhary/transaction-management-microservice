package com.example.transaction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 Java Record: Records are immutable data carriers and perfect for DTOs.

 They automatically generate:
 - constructor
 - getters
 - equals/hashCode
 - toString
*/

public record LoanRequest(

        @NotNull
        Long customerId,

        @NotNull
        @Min(1)
        BigDecimal amount

) {}