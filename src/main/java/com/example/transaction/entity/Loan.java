package com.example.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/*
 Loan entity demonstrates real enterprise workflow:

 Customer applies for loan
 → system validates credit score
 → transaction commits loan record
*/

@Entity
@Table(name = "loans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal amount;

    private String status;

    /*
     Many loans belong to one customer
    */
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}