package com.demo.budget_approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.budget_approval.entity.AuditLog;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByEntityType(String entityType);
}
