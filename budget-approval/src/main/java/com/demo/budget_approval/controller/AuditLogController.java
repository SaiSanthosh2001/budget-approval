package com.demo.budget_approval.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.budget_approval.entity.AuditLog;
import com.demo.budget_approval.repository.AuditLogRepository;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {
    
	@Autowired
	private AuditLogRepository repo;
  
    public AuditLogController(AuditLogRepository repo) { this.repo = repo; }

    // GET 
    @GetMapping
    public ResponseEntity<List<AuditLog>> list(@RequestParam(required = false) String entityType) {
        if (entityType == null || entityType.isBlank()) return ResponseEntity.ok(repo.findAll());
        return ResponseEntity.ok(repo.findByEntityType(entityType));
    }
}
