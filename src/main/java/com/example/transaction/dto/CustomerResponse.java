package com.example.transaction.dto;

import java.math.BigDecimal;

/*
 Response DTO used to avoid exposing entity objects directly.

 This is a best practice in enterprise systems.
*/

public record CustomerResponse(

        Long id,
        String name,
        BigDecimal creditScore,
        BigDecimal accountBalance

) {}