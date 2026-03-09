package com.example.transaction.controller;

import com.example.transaction.service.ConcurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/*
 Controller exposing APIs to simulate database concurrency scenarios.

 What is Database Concurrency?

 Concurrency occurs when multiple transactions access or modify the same database resources at the same time.
 Proper concurrency control ensures data consistency and prevents conflicts such as lost updates or deadlocks.

 These APIs demonstrate how databases behave when several transactions execute simultaneously.

 Scenarios demonstrated through this controller:

 - Deadlock situations
 - Concurrent transaction execution
 - Stress testing with multiple threads
 - Locking behavior (optimistic and pessimistic)

 These endpoints help understand how transaction management, isolation levels,
 locking strategies, and concurrency control work in real-world systems.

--------------------------------------------------------------------------
How to Test Concurrency (Recommended Methods)

 1. Postman Runner

 Run the endpoint multiple times:
 POST /api/concurrency/stress-test-transfers?threads=50


 2. curl loop

 for i in {1..20}; do
 curl -X POST http://localhost:8080/api/concurrency/concurrent &
 done


 3. JMeter / k6

 Simulate 100+ concurrent users hitting:
 /api/concurrency/stress-test-loans
--------------------------------------------------------------------------

 This microservice now demonstrates:

 - Deadlocks
 - Concurrent transactions
 - Stress testing
 - Optimistic locking conflicts
 - Pessimistic locking blocking
 - Thread-based concurrency simulation
*/

@RestController
@RequestMapping("/api/v1/concurrency")
@RequiredArgsConstructor
public class ConcurrencyController {

    private final ConcurrencyService concurrencyService;

    /*
     Simulates a deadlock scenario.

     A deadlock occurs when two transactions wait for each other indefinitely while holding locks on resources.

     Example scenario:
     Transaction 1 locks resource A and waits for resource B.
     Transaction 2 locks resource B and waits for resource A.

     Endpoint:
     POST /api/concurrency/deadlock

     How to test:
     Call this endpoint multiple times simultaneously using Postman Runner or a load testing tool.
    */
    @PostMapping("/deadlock")
    public String simulateDeadlock() {

        concurrencyService.simulateDeadlock();
        return "Deadlock simulation started";
    }

    /*
     Simulates concurrent transaction execution.

     Multiple threads are started to represent several transactions running at the same time.

     Endpoint:
     POST /api/concurrency/concurrent

     How to test:
     Send several requests quickly or run this endpoint using Postman Collection Runner.
    */
    @PostMapping("/concurrent")
    public String simulateConcurrentTransactions() {

        concurrencyService.simulateConcurrentUpdates();
        return "Concurrent transactions started";
    }

    /*
     Runs a concurrency stress test for money transfers.

     Multiple threads execute transfer operations concurrently.
     This demonstrates pessimistic locking and row-level blocking.

     Endpoint:
     POST /api/concurrency/stress-test-transfers?threads=50

     Example:
     POST /api/concurrency/stress-test-transfers?threads=50

     How to test:
     Increase the thread count to simulate heavy load and observe locking behavior in the database.
    */
    @PostMapping("/stress-test-transfers")
    public String stressTestTransfers(@RequestParam int threads) {

        concurrencyService.stressTestTransfers(threads);
        return "Transfer stress test started with " + threads + " threads";
    }

    /*
     Runs a concurrency stress test for loan approvals.

     Multiple threads apply for loans simultaneously to simulate high-concurrency transaction scenarios.

     This helps demonstrate optimistic locking conflicts.

     Endpoint:
     POST /api/concurrency/stress-test-loans?threads=20

     How to test:
     Send requests with higher thread counts and observe optimistic locking conflicts or retry behavior.
    */
    @PostMapping("/stress-test-loans")
    public String stressTestLoans(@RequestParam int threads) {

        concurrencyService.stressTestLoanApprovals(threads);
        return "Loan stress test started with " + threads + " threads";
    }
}