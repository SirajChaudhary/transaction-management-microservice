package com.example.transaction.repository;

import com.example.transaction.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/*
 Repository responsible for database operations related to Customer entity.

 Spring Data JPA automatically generates SQL queries for standard CRUD operations.

 We can also define custom queries using:
 1. JPQL (Java Persistence Query Language)
 2. Native SQL
*/

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /*
     JPQL QUERY

     JPQL works with ENTITY OBJECTS instead of database tables.

     Use JPQL when:
     - working with entity relationships
     - database independence
     - using ORM features

     Example: finding customers with high credit score
    */
    @Query("SELECT c FROM Customer c WHERE c.creditScore > :score")
    List<Customer> findCustomersWithHighCredit(BigDecimal score);


    /*
     NATIVE SQL QUERY

     Use native SQL when:
     - using database-specific features
     - using complex analytics queries
     - performance tuning
     - using window functions

     Example: find rich customers
    */
    @Query(value = "SELECT * FROM customers WHERE account_balance > :balance",
            nativeQuery = true)
    List<Customer> findRichCustomers(BigDecimal balance);
}