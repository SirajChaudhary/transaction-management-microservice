package com.example.transaction.controller;

import com.example.transaction.entity.Loan;
import com.example.transaction.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/*
 Controller exposing Loan processing APIs.

 This controller demonstrates an ACID compliant transaction workflow.

 Loan approval involves multiple operations executed within a single transaction:

 - validating customer eligibility
 - creating a loan record
 - recording audit logs

 If any operation fails, the entire transaction rolls back, ensuring database consistency.
*/

@RestController
@RequestMapping("/api/v1/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /*
     Applies for a loan for the specified customer.

     The entire workflow executes inside a transactional service method.
    */
    @PostMapping("/apply")
    public ResponseEntity<Loan> applyLoan(
            @RequestParam Long customerId,
            @RequestParam BigDecimal amount) {

        return ResponseEntity.ok(
                loanService.applyLoan(customerId, amount)
        );
    }
}