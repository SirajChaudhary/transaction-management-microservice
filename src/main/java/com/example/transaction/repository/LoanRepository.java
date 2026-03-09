package com.example.transaction.repository;

import com.example.transaction.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

/*
 Repository responsible for executing database queries for Loan entity.

 Demonstrates:

 - JPQL join queries using entity relationships
 - Analytics queries using SQL window functions

 These examples show how relational databases support both transactional queries and analytical queries.
*/

public interface LoanRepository extends JpaRepository<Loan, Long> {

    /*
     JOIN QUERY USING JPQL

     JPQL works with entity relationships instead of database tables.

     Here we join the Loan entity with its associated Customer entity.

     Relationship used:
     Loan -> Customer

     Example scenario:
     Retrieve all loans where the associated customer's credit score is greater than the given threshold.

     This query is database independent because JPQL operates on the entity model rather than physical tables.
    */
    @Query("""
           SELECT l
           FROM Loan l
           JOIN l.customer c
           WHERE c.creditScore > :score
           """)
    List<Loan> findLoansForHighCreditCustomers(BigDecimal score);


    /*
     ANALYTICS QUERY USING WINDOW FUNCTION

     What is a Window Function?

     A window function is an advanced SQL feature that performs calculations across a group of rows related to the current row,
     while still returning every individual row in the result set.

     Unlike GROUP BY, which aggregates rows and returns one row per group,
     window functions keep all original rows and attach the calculated value to each row.

     Query used in this example:

     SUM(amount) OVER (PARTITION BY customer_id)

     Explanation:

     SUM(amount)
     Calculates the total loan amount.

     OVER(...)
     Indicates that the calculation is a window function.

     PARTITION BY customer_id
     Divides the rows into logical groups based on customer_id.
     The calculation is performed separately for each customer, but all rows are still returned.

     Example loans table:

     | id | customer_id | amount |
     |----|-------------|--------|
     | 1  | 1           | 1000   |
     | 2  | 1           | 2000   |
     | 3  | 1           | 2000   |
     | 4  | 2           | 3000   |

     Query result:

     | customer_id | amount | total_loans |
     |-------------|--------|-------------|
     | 1           | 1000   | 5000        |
     | 1           | 2000   | 5000        |
     | 1           | 2000   | 5000        |
     | 2           | 3000   | 3000        |

     Each row remains in the result set, but the total loan amount for that customer is attached to every row belonging to that customer.

     Window functions are commonly used for:
     - financial analytics
     - running totals
     - ranking records
     - cumulative calculations
     - reporting and business intelligence queries

     Because window functions are database-specific features, we use a native SQL query instead of JPQL.
    */
    @Query(value = """
            SELECT customer_id,
                   SUM(amount) OVER(PARTITION BY customer_id) AS total_loans
            FROM loans
            """,
            nativeQuery = true)
    List<Object[]> loanAnalytics();
}