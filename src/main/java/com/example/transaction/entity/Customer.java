package com.example.transaction.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

/*
 Entity demonstrating optimistic locking.

 Version column (@Version) used for Optimistic Locking.

 Optimistic locking prevents lost updates in concurrent transactions.

 When two transactions update the same record, Hibernate checks the version column and throws OptimisticLockException if conflict occurs.
*/

@Entity
@Table(name = "customers")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private BigDecimal creditScore;

    private BigDecimal accountBalance;

    /*
     OPTIMISTIC LOCK

     Version column used for Optimistic Locking.

     Hibernate automatically increments this value whenever the entity is updated.
    */
    @Version
    private Long version;

    /*
     One customer can have multiple loans (1-to-n relationship)
    */
    @OneToMany(mappedBy = "customer")
    @JsonIgnore
    private List<Loan> loans;

}