package com.demo.budget_approval.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.budget_approval.entity.BudgetRequest;

import java.time.Instant;
import java.util.List;

public interface BudgetRequestRepository extends JpaRepository<BudgetRequest, Long> {
    List<BudgetRequest> findByStatus(String status);
    boolean existsByPurposeIgnoreCaseAndDateCreatedAfter(String purpose, Instant after);
}
