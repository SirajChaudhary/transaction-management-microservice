package com.example.transaction.service;

/*
 Audit logging demonstrates transaction propagation REQUIRES_NEW.

 Audit logs should commit even if main transaction fails.
*/

public interface AuditService {

    void logAction(String action, String status);
}