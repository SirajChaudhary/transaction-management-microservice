package com.example.transaction.service.impl;

import com.example.transaction.service.ConcurrencyService;
import com.example.transaction.service.LoanService;
import com.example.transaction.service.LockingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 Service implementation executing concurrency simulations.

 This class demonstrates how concurrent database transactions behave when multiple threads interact with shared resources.

 Scenarios demonstrated:

 - Deadlock simulation
 - Concurrent transaction execution
 - Stress testing with multiple threads
 - Locking behavior (optimistic and pessimistic)
*/

@Service
@RequiredArgsConstructor
public class ConcurrencyServiceImpl implements ConcurrencyService {

    private final LockingService lockingService;
    private final LoanService loanService;

    /*
     Simulates a deadlock scenario.

     Two threads attempt to acquire locks on shared resources in opposite order which can cause a deadlock.

     Transaction 1:
     - locks resource1
     - waits for resource2

     Transaction 2:
     - locks resource2
     - waits for resource1
    */
    @Override
    public void simulateDeadlock() {

        Object resource1 = new Object();
        Object resource2 = new Object();

        Thread t1 = new Thread(() -> {

            synchronized (resource1) {

                System.out.println("Transaction 1 locked resource1");

                try { Thread.sleep(100); } catch (InterruptedException ignored) {}

                synchronized (resource2) {

                    System.out.println("Transaction 1 locked resource2");
                }
            }
        });

        Thread t2 = new Thread(() -> {

            synchronized (resource2) {

                System.out.println("Transaction 2 locked resource2");

                try { Thread.sleep(100); } catch (InterruptedException ignored) {}

                synchronized (resource1) {

                    System.out.println("Transaction 2 locked resource1");
                }
            }
        });

        t1.start();
        t2.start();
    }

    /*
     Simulates concurrent transaction execution.

     Multiple threads are created to mimic several database
     transactions executing simultaneously.
    */
    @Override
    public void simulateConcurrentUpdates() {

        for (int i = 0; i < 5; i++) {

            new Thread(() ->
                    System.out.println("Concurrent transaction executing: "
                            + Thread.currentThread().getName())
            ).start();
        }
    }

    /*
     Executes transfer operations concurrently.

     Multiple threads perform money transfers at the same time,
     demonstrating pessimistic locking behavior.
    */
    @Override
    public void stressTestTransfers(int threads) {

        runConcurrentTransactions(threads, () ->
                lockingService.pessimisticTransfer(
                        1L,
                        2L,
                        new BigDecimal("10")
                )
        );
    }

    /*
     Executes loan approval operations concurrently.

     Multiple threads attempt to update the same customer
     which can cause optimistic locking conflicts.
    */
    @Override
    public void stressTestLoanApprovals(int threads) {

        runConcurrentTransactions(threads, () ->
                loanService.applyLoan(
                        1L,
                        new BigDecimal("1000")
                )
        );
    }

    /*
     Utility method used internally to execute tasks concurrently.

     Purpose:

     Creates a fixed thread pool and executes the provided task
     multiple times in parallel. Each thread represents a separate
     transaction interacting with the database.

     This helps simulate real-world scenarios where many clients
     access the system simultaneously.
    */
    private void runConcurrentTransactions(int threads, Runnable task) {

        ExecutorService executor = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {

            executor.submit(() -> {

                System.out.println(
                        "Running transaction in thread: "
                                + Thread.currentThread().getName());

                task.run();
            });
        }

        executor.shutdown();
    }
}