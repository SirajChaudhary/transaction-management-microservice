package com.example.transaction.repository;

import com.example.transaction.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

/*
 Simple repository used for audit logging.

 Used with transaction propagation REQUIRES_NEW.
*/

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
}