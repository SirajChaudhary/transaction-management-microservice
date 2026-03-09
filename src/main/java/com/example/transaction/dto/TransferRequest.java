package com.example.transaction.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

/*
 DTO used for demonstrating money transfer between accounts.

 This scenario will later demonstrate:

 - ACID transactions
 - Isolation levels
 - Pessimistic locking
*/

public record TransferRequest(

        @NotNull
        Long fromAccountId,

        @NotNull
        Long toAccountId,

        @NotNull
        @Min(1)
        BigDecimal amount

) {}