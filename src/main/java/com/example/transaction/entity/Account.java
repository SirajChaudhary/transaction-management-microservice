package com.example.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

/*
 Account entity is used to simulate money transfers between accounts to demonstrate:

 - transaction propagation
 - isolation levels
 - pessimistic locking
*/

@Entity
@Table(name = "accounts")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

}