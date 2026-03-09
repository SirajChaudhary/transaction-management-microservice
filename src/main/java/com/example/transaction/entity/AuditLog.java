package com.example.transaction.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/*
 AuditLog demonstrates transaction propagation
 with REQUIRES_NEW.

 Even if main transaction fails,
 audit logs should still be committed.
*/

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String action;

    private String status;

    private LocalDateTime createdAt;

}