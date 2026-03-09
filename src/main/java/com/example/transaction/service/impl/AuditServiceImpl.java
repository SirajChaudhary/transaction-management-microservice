package com.example.transaction.service.impl;

import com.example.transaction.entity.AuditLog;
import com.example.transaction.repository.AuditLogRepository;
import com.example.transaction.service.AuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/*
 Service implementation demonstrating transaction propagation: REQUIRES_NEW.

 This service is responsible for persisting audit log records.

 REQUIRES_NEW ensures that the audit log is saved in a completely independent transaction.
*/

@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditLogRepository auditLogRepository;

    /*
     Demonstrates REQUIRES_NEW propagation.

     Saves an audit record using a separate transaction.

     Even if the calling transaction fails or rolls back, this audit record will still be committed.
    */
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
}