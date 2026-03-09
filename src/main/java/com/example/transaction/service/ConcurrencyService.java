package com.example.transaction.service;

/*
 Service interface defining operations used to simulate database concurrency scenarios.
*/

public interface ConcurrencyService {

    // Simulates a deadlock scenario between two transactions.
    void simulateDeadlock();

    // Simulates concurrent transaction execution.
    void simulateConcurrentUpdates();

    // Runs a concurrency stress test for transfer operations.
    void stressTestTransfers(int threads);

    // Runs a concurrency stress test for loan approvals.
    void stressTestLoanApprovals(int threads);
}