# Database Transaction Management Microservice

This microservice demonstrates **advanced database transaction management** using **Spring Boot and Spring Data JPA**.

The goal of this example is to act as a **transaction laboratory** that shows how modern backend systems maintain:

- **data consistency**

- **high-concurrency safety**

- **transaction reliability**

- **database integrity**

in real-world financial workflows.

---

# Key Concepts

This microservice demonstrates:

- **ACID compliant database transactions** (ACID transaction guarantees)
- **Transaction propagation strategies** (all 7 Spring propagation types)
- **Transaction isolation levels** (all 4 SQL isolation levels)
- **Locking mechanisms**
    - **Optimistic locking**
    - **Pessimistic locking**
- **Concurrency control**
  - **Deadlock simulation**
  - **Concurrency stress testing**
- **Analytics queries**
    - **SQL Window functions**
- **Native SQL queries**
- **JPQL queries**
- **Join queries with JPA entities**
- **Connection pooling using HikariCP**
- **Transaction monitoring with Prometheus**
- **Audit logging**
- **Transaction rollback scenarios**
- **REST based transaction workflows**
- **Enterprise-style layered architecture**

---

# Technology Stack

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate ORM**
- **PostgreSQL**
- **HikariCP** (Connection Pool)
- **JPQL Queries**
- **Native SQL Queries**
- **Window Functions**
- **REST APIs**
- **Java Records**
- **Lombok**
- **Stream API**
- **Global Exception Handling**
- **Micrometer + Prometheus (metrics monitoring)**
- **Maven**
- **Postman**

---

# Project Structure

```
transaction-management-microservice
 ├── controller
 ├── service
 │     ├── impl
 ├── repository
 ├── entity
 ├── dto
 ├── exception
 ├── query
 └── util
```

This structure follows **clean layered architecture**:
```
Controller → Service → Repository → Database
```

### Design Patterns Used

- Layered Architecture

- Builder Pattern

- Repository Pattern

- Service Pattern

- DTO Pattern

- Global Exception Pattern

- Utility Pattern

These patterns ensure the code is:

- clean

- maintainable

- scalable

- production ready

---

# ACID Transactions

ACID is the foundation of reliable database systems.

ACID stands for:

- Atomicity
- Consistency
- Isolation
- Durability

### Atomicity

Atomicity ensures that **all operations in a transaction either succeed or fail together**.

Example:

When applying for a loan:

1. Validate customer
2. Create loan record
3. Insert audit log

If any step fails, **the entire transaction rolls back**.

Example code:

```java
@Transactional
public Loan applyLoan(Long customerId, BigDecimal amount) {
    Customer customer = customerRepository.findById(customerId)
            .orElseThrow();

    Loan loan = Loan.builder()
            .customer(customer)
            .amount(amount)
            .status("APPROVED")
            .build();

    return loanRepository.save(loan);
}
```

### Consistency

Consistency ensures that **database constraints remain valid after a transaction**.

Example rules:

- Loan cannot be created for an invalid customer
- Credit score must be above threshold
- Account balances remain valid

The database always moves from **one consistent state to another**.

### Isolation

Isolation ensures that **concurrent transactions do not interfere with each other**.

Isolation levels demonstrated in this microservice:

- READ_UNCOMMITTED
- READ_COMMITTED
- REPEATABLE_READ
- SERIALIZABLE

Example:

```java
@Transactional(isolation = Isolation.SERIALIZABLE)
public void serializableExample() {
}
```

Each level provides different levels of **data visibility protection**.

### Durability

Durability ensures that **once a transaction commits, the data is permanently stored**.

PostgreSQL guarantees durability using:

- Write Ahead Logging (WAL)
- Disk persistence

---

# Transaction Propagation

Transaction propagation defines **how transactions behave when one transactional method calls another**.

Spring supports **7 propagation strategies**.

### REQUIRED (Default)

- Joins existing transaction
- Creates new transaction if none exists

### REQUIRES_NEW

- Always starts a new transaction
- Suspends existing transaction

Used for **audit logging**.

Example:

```java
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void logAction(String action, String status) {
}
```

### NESTED

- Creates nested transaction using savepoints
- Allows partial rollback

### SUPPORTS

- Executes within a transaction if one exists
- Otherwise runs without transaction

### NOT_SUPPORTED

- Always runs without transaction
- Suspends existing transaction

### MANDATORY

- Requires existing transaction
- Throws exception if none exists

### NEVER

- Must run without transaction
- Throws exception if transaction exists

---

# Transaction Isolation

Isolation defines **how and when changes made by one transaction become visible to other concurrent transactions**.

Transaction **Isolation** is one of the **ACID properties** of database transactions.

In systems where multiple transactions run simultaneously, isolation levels prevent issues such as:

- **Dirty Reads** – Reading uncommitted data written by another transaction.
- **Non-repeatable Reads** – Reading the same row multiple times and getting different values.
- **Phantom Reads** – Executing the same query twice and getting different result rows.
- **Lost Updates** – Two transactions update the same row and one update overwrites the other.

### Isolation Levels

SQL defines four standard isolation levels.

| Isolation Level | Prevents |
|----------------|----------|
| READ_UNCOMMITTED | Nothing |
| READ_COMMITTED | Dirty Reads |
| REPEATABLE_READ | Dirty Reads, Non-repeatable Reads |
| SERIALIZABLE | Dirty Reads, Non-repeatable Reads, Phantom Reads |

Higher isolation levels provide **stronger consistency but may reduce performance**.

### READ_COMMITTED

A transaction can only read data that has already been **committed** by other transactions.

Prevents:

- Dirty Reads

But still allows:

- Non-repeatable Reads
- Phantom Reads
- Lost Updates

Example:

```java
@Override
@Transactional(isolation = Isolation.READ_COMMITTED)
public void readCommittedExample() {
    
}
```

### REPEATABLE_READ

Ensures that rows read during a transaction **cannot change until the transaction completes**.

Prevents:

- Dirty Reads
- Non-repeatable Reads

May still allow:

- Phantom Reads

Example:

```java
@Override
@Transactional(isolation = Isolation.REPEATABLE_READ)
public void repeatableReadExample() {
    
}
```

### SERIALIZABLE

The **strictest isolation level**.

Transactions behave as if they run **one after another sequentially**.

Prevents:

- Dirty Reads
- Non-repeatable Reads
- Phantom Reads
- Lost Updates

Example:

```java
@Override
@Transactional(isolation = Isolation.SERIALIZABLE)
public void serializableExample() {
    System.out.println("SERIALIZABLE isolation level executed");
}
```

### DEFAULT

Spring also provides `DEFAULT`, which uses the **database default isolation level**.

Example:

- PostgreSQL default → **READ_COMMITTED**

```java
@Override
@Transactional
public void defaultIsolationExample() {
    
}
```

---

# Locking

Locking is used to **control concurrent access to database records**.

When multiple transactions update the same data simultaneously, locking prevents:

- lost updates
- inconsistent writes
- race conditions

This microservice demonstrates:

- Optimistic Locking
- Pessimistic Locking

### Optimistic Locking

Optimistic locking assumes **conflicts between transactions are rare**.

Instead of locking the database row, the system checks if the record was modified by another transaction before committing.

Implemented using **@Version**.

```java
@Version
private Long version;
```

If two transactions update the same row:

- first transaction succeeds
- second transaction throws **OptimisticLockException**

### Pessimistic Locking

Pessimistic locking locks a database row **before performing updates**.  
This ensures that other transactions cannot modify the same row until the lock is released.

This approach is useful when the probability of **concurrent updates is high**.

Example repository query using **PESSIMISTIC_WRITE** lock:

```java
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("SELECT a FROM Account a WHERE a.id = :id")
Optional<Account> findAccountForUpdate(Long id);
```

Explanation:

- `PESSIMISTIC_WRITE` places a **write lock** on the selected row.
- While the transaction is active:
    - Other transactions **cannot update the same row**
    - Other write operations **must wait until the lock is released**

Common real-world use cases:

- bank money transfers
- inventory deduction
- ticket booking systems
- financial ledger updates

---

# Audit Logging

Enterprise systems maintain **audit logs** to track important system events.

Examples of events that should be logged:

- loan approvals
- loan rejections
- security events
- financial operations

In this project we created an **AuditLog entity** that stores:

- action
- status
- timestamp

Example usage inside the loan service:

```
auditService.logAction("LOAN_APPROVED", "SUCCESS");
```

or

```
auditService.logAction("LOAN_REJECTED", "FAILED");
```

Implementation:

```java
@Override
@Transactional(propagation = Propagation.REQUIRES_NEW)
public void logAction(String action, String status) {

    AuditLog log = AuditLog.builder()
            .action(action)
            .status(status)
            .createdAt(LocalDateTime.now())
            .build();

    auditLogRepository.save(log);
}
```

Why `REQUIRES_NEW`?

- Audit logs must be **saved independently**
- Even if the main transaction fails, the audit record should still exist

Example scenario:

Loan approval fails → transaction rollback  
Audit log still saved → failure event recorded

This is a **common enterprise pattern for financial systems**.

---

# Window Function

A **window function** is an advanced SQL feature used for **analytics queries**.

Unlike `GROUP BY`, window functions:

- do **not collapse rows**
- return every row
- attach calculated values to each row

Example query used in this example:

```
SELECT customer_id,
SUM(amount) OVER(PARTITION BY customer_id)
FROM loans;
```

Explanation:

- `SUM(amount)` → calculates total loan amount
- `OVER(...)` → defines the window
- `PARTITION BY customer_id` → groups rows logically by customer

Example table:

| id | customer_id | amount |
|----|-------------|--------|
|1|1|1000|
|2|1|2000|
|3|1|2000|
|4|2|3000|

Query result:

| customer_id | total_loans |
|-------------|-------------|
|1|5000|
|1|5000|
|1|5000|
|2|3000|

Use cases:

- financial analytics
- reporting dashboards
- running totals
- ranking records
- business intelligence queries

This project implements the window function in **LoanRepository** using a **native SQL query**.

---

# Concurrency

Concurrency occurs when **multiple transactions run at the same time**.

In high traffic systems many users may attempt to update the same database records simultaneously.

Examples:

- multiple bank transfers
- loan applications
- inventory purchases

This microservice includes **concurrency simulation APIs**.

### Deadlock Simulation

A **deadlock** occurs when two transactions wait for each other indefinitely.

Example scenario:

Transaction 1:

- locks **Account A**
- waits for **Account B**

Transaction 2:

- locks **Account B**
- waits for **Account A**

Both transactions wait forever → deadlock occurs.

The microservice includes APIs to simulate such behavior.

### Concurrency Stress Testing

The project also supports **stress testing using multiple threads**.

Example API:

```
POST /api/v1/concurrency/stress-test-transfers?threads=50
```

This creates multiple concurrent transactions to test:

- locking behavior
- optimistic locking conflicts
- pessimistic locking blocking
- transaction consistency under load

---

# JPQL vs Native SQL

Spring Data JPA supports two ways to write queries:

1. JPQL
2. Native SQL

### JPQL

JPQL works with **entity objects instead of database tables**.

Example:

```java
@Query("SELECT c FROM Customer c WHERE c.creditScore > :score")
List<Customer> findHighCreditCustomers(BigDecimal score);
```

Advantages:

- database independent
- works with ORM entities
- easier to maintain

### Native SQL

Native SQL executes **actual database queries**.

Example:

```java
@Query(
 value = "SELECT * FROM customers WHERE account_balance > :balance",
 nativeQuery = true
)
List<Customer> findRichCustomers(BigDecimal balance);
```

Used when:

- complex SQL queries are required
- database-specific features are needed
- performance tuning is required
- window functions are used

---

# Connection Pooling (HikariCP)

Database connections are expensive to create.

Connection pooling improves performance by **reusing connections**.

Spring Boot uses **HikariCP** as the default connection pool.

Example configuration:

```
spring.datasource.hikari.maximum-pool-size=20
```

Benefits:

- faster database access
- reduced latency
- better resource management

Monitoring endpoints:

```
/actuator/metrics
/actuator/prometheus
```

---

# How To Run

Clone the repository:

```
git clone https://github.com/SirajChaudhary/transaction-management-microservice.git
```

Create database:

```
CREATE DATABASE transactiondb;
```

Create tables and load sample data with scripts:

```
resources/db/schema.sql
resources/db/data.sql
```

Run the application:

```
TransactionManagementMicroserviceApplication.java
```

Import the Postman collection and test the APIs.

---

# API Endpoints

### Initial Sample Data (via Scripts)

<img width="1142" height="366" alt="image" src="https://github.com/user-attachments/assets/39758054-6e25-41c1-aec5-ddab343b807b" />
<br /> <br />
<img width="654" height="374" alt="image" src="https://github.com/user-attachments/assets/037c8cc1-05a5-46bf-a798-237d21a853ae" />
<br /> <br />
<img width="898" height="274" alt="image" src="https://github.com/user-attachments/assets/db544817-f4f3-4024-9da9-419b1e1d1f1d" />
<br /> <br />
<img width="1236" height="276" alt="image" src="https://github.com/user-attachments/assets/ebc68509-a7d4-46b6-a751-5dd4708848c7" />

### Customer APIs

```
POST /api/v1/customers?name={name}&creditScore={creditScore}&balance={balance}
```
<img width="1698" height="1000" alt="image" src="https://github.com/user-attachments/assets/a57aba93-24a6-44fe-a322-5970e9c30c2a" />
<br /> <br />
<img width="1170" height="418" alt="image" src="https://github.com/user-attachments/assets/993a4d31-5ab5-416b-aeb1-d21d50da8db3" />
<br /> <br />

```
GET /api/v1/customers/{id}
```
<img width="1714" height="800" alt="image" src="https://github.com/user-attachments/assets/55c447d2-3a03-4571-9549-dfc5bc7df1cb" />
<br /> <br />

```
GET /api/v1/customers/high-credit?score={creditScore}
```
<img width="1698" height="1186" alt="image" src="https://github.com/user-attachments/assets/36075429-4a11-409c-bce6-44ab7902dbe1" />
<br /> <br />

```
GET /api/v1/customers/rich?balance={balance}
```
<img width="1694" height="1444" alt="image" src="https://github.com/user-attachments/assets/b09e3a58-dd73-48b3-88cc-c7f033fd75c6" />

### Loan APIs

```
POST /api/v1/loans/apply?customerId={id}&amount={amount}
```
<img width="1704" height="1110" alt="image" src="https://github.com/user-attachments/assets/203621a3-75cb-42ac-a174-32b8d6b429c6" />
<br /> <br />
<img width="978" height="320" alt="image" src="https://github.com/user-attachments/assets/caca520a-b8ed-4741-af8c-64992a566ba5" />
<br /> <br />
<img width="1254" height="308" alt="image" src="https://github.com/user-attachments/assets/1ed42477-7bad-464c-b894-7ced90e358e3" />
<br /> <br />

### Locking APIs

```
POST /api/v1/locking/optimistic?customerId={id}&amount={amount}
```
<img width="1704" height="724" alt="image" src="https://github.com/user-attachments/assets/426d0fe0-ae2d-4105-998e-715f93f03ab5" />
<br /> <br />
<img width="1170" height="418" alt="image" src="https://github.com/user-attachments/assets/b304cb6d-43f4-4b23-94c2-9382c58cbe71" />
<br /> <br />

```
POST /api/v1/locking/pessimistic-transfer?fromAccountId={id}&toAccountId={id}&amount={amount}
```
<img width="1702" height="784" alt="image" src="https://github.com/user-attachments/assets/91b1d884-e616-4f2e-9ad2-d294e6f7e968" />
<br /> <br />
<img width="666" height="366" alt="image" src="https://github.com/user-attachments/assets/1e272f0c-d236-4210-b516-bce3c0d4a347" />
<br /> <br />

### Isolation APIs
<i>Note: These APIs are only to learn the concept of Transaction Isolation (check the code) and do not perform any operation in this example.</i>
```
POST /api/v1/isolation/default

POST /api/v1/isolation/read-committed

POST /api/v1/isolation/repeatable-read

POST /api/v1/isolation/serializable
```
<img width="1706" height="584" alt="image" src="https://github.com/user-attachments/assets/db432776-2a4a-4f78-bbb2-12b15da574ca" />
<br /> <br />

### Propagation APIs
<i>Note: These APIs are only to learn the concept of Transaction Propagation (check the code) and do not perform any operation in example.</i>
```
POST /api/v1/transactions/required

POST /api/v1/transactions/requires-new

POST /api/v1/transactions/nested

POST /api/v1/transactions/supports

POST /api/v1/transactions/not-supported

POST /api/v1/transactions/mandatory

POST /api/v1/transactions/never
```
<img width="1708" height="594" alt="image" src="https://github.com/user-attachments/assets/a54723d1-0b7b-496f-a466-2b69fd0528ca" />
<br /> <br />
<img width="1254" height="358" alt="image" src="https://github.com/user-attachments/assets/a12170df-f310-4648-89a4-b09c07f85dcc" />
<br /> <br />

### Concurrency APIs
<i>Note: These APIs are only to learn the concept of Concurrency (check the code) and do not perform any operation in this example.</i>
```
POST /api/v1/concurrency/deadlock

POST /api/v1/concurrency/concurrent

POST /api/v1/concurrency/stress-test-transfers?threads={count}

POST /api/v1/concurrency/stress-test-loans?threads={count}
```
<img width="1712" height="594" alt="image" src="https://github.com/user-attachments/assets/c8e05e2e-eb25-458e-9a65-6734350d1bd7" />
<br /> <br />

### Analytics APIs

```
GET /api/v1/analytics/loan-summary
```
<img width="1702" height="780" alt="image" src="https://github.com/user-attachments/assets/2db11672-17b3-45b4-851d-183b97175b51" />
<br /> <br />

### Actuator Monitoring APIs
```
GET /actuator/health

GET /actuator/metrics

GET /actuator/prometheus

GET /actuator/metrics/jdbc.connections.active

GET /actuator/metrics/hikaricp.connections.active
```
<img width="1700" height="798" alt="image" src="https://github.com/user-attachments/assets/a2b0ce76-54df-40a1-b4e9-e0ae14a8513f" />
<br /> <br />

---

# Summary

This microservice demonstrates **enterprise database transaction management** including:

- ACID guarantees
- transaction propagation
- isolation levels
- optimistic locking
- pessimistic locking
- deadlock simulation
- concurrency stress testing
- analytics queries
- window functions
- JPQL and native SQL queries
- connection pool monitoring

---
# License

Free software, by [Siraj Chaudhary](https://www.linkedin.com/in/sirajchaudhary/)
