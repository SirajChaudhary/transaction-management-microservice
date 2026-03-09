package com.example.transaction.service.impl;

import com.example.transaction.service.AuditService;
import com.example.transaction.service.TransactionPropagationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/*
 Service implementation applying all 7 transaction propagation strategies using Spring's @Transactional annotation.
*/

@Service
@RequiredArgsConstructor
public class TransactionPropagationServiceImpl implements TransactionPropagationService {

    private final AuditService auditService;

    /*
     Executes logic using REQUIRED propagation.

     If a transaction already exists it will join that transaction, otherwise a new transaction will be created.
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void requiredExample() {

        auditService.logAction("REQUIRED_TX", "STARTED");
    }

    /*
     Executes logic using REQUIRES_NEW propagation.

     Always starts a new independent transaction. If an existing transaction is present it will be suspended until the new transaction completes.
    */
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void requiresNewExample() {

        auditService.logAction("REQUIRES_NEW_TX", "STARTED");
    }

    /*
     Executes logic using NESTED propagation.

     Runs inside a nested transaction using a savepoint. The nested transaction can roll back independently without affecting the outer transaction.
    */
    @Override
    @Transactional(propagation = Propagation.NESTED)
    public void nestedExample() {

        auditService.logAction("NESTED_TX", "STARTED");
    }

    /*
     Executes logic using SUPPORTS propagation.

     If a transaction exists it will participate in that transaction, otherwise the method executes without a transaction.
    */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void supportsExample() {

        auditService.logAction("SUPPORTS_TX", "STARTED");
    }

    /*
     Executes logic using NOT_SUPPORTED propagation.

     Always executes without a transaction. If a transaction exists it will be suspended temporarily.
    */
    @Override
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void notSupportedExample() {

        auditService.logAction("NOT_SUPPORTED_TX", "STARTED");
    }

    /*
     Executes logic using MANDATORY propagation.

     Requires an existing transaction. If no transaction exists an exception will be thrown.
    */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void mandatoryExample() {

        auditService.logAction("MANDATORY_TX", "STARTED");
    }

    /*
     Executes logic using NEVER propagation.

     Executes only when no transaction exists. If a transaction is present an exception will be thrown.
    */
    @Override
    @Transactional(propagation = Propagation.NEVER)
    public void neverExample() {

        auditService.logAction("NEVER_TX", "STARTED");
    }
}