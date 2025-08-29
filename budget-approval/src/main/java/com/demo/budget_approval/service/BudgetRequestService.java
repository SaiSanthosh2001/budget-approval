package com.demo.budget_approval.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.budget_approval.dto.BudgetRequestCreateDTO;
import com.demo.budget_approval.entity.AuditLog;
import com.demo.budget_approval.entity.BudgetRequest;
import com.demo.budget_approval.entity.Department;
import com.demo.budget_approval.entity.Manager;
import com.demo.budget_approval.repository.AuditLogRepository;
import com.demo.budget_approval.repository.BudgetRequestRepository;
import com.demo.budget_approval.repository.DepartmentRepository;
import com.demo.budget_approval.repository.ManagerRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class BudgetRequestService {

	@Autowired
    private  BudgetRequestRepository budgetRepo;
	@Autowired
	private  DepartmentRepository deptRepo;
	@Autowired
	private  ManagerRepository managerRepo;
	@Autowired
	private  AuditLogRepository auditRepo;

    public BudgetRequestService(BudgetRequestRepository budgetRepo,
                                DepartmentRepository deptRepo,
                                ManagerRepository managerRepo,
                                AuditLogRepository auditRepo) {
        this.budgetRepo = budgetRepo;
        this.deptRepo = deptRepo;
        this.managerRepo = managerRepo;
        this.auditRepo = auditRepo;
    }

    @Transactional
    public BudgetRequest create(BudgetRequestCreateDTO dto) {
        Department dept = deptRepo.findById(dto.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Department not found"));

        // positive amount
        if (dto.getRequestedAmount() == null || dto.getRequestedAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("requestedAmount must be positive");
        }

        // 10% of yearly allocation
        BigDecimal tenPercent = dept.getYearlyAllocation().multiply(new BigDecimal("0.10"));
        if (dto.getRequestedAmount().compareTo(tenPercent) > 0) {
            throw new IllegalArgumentException("requestedAmount exceeds 10% of yearlyAllocation");
        }

        // duplicate purpose within 7 days
        Instant sevenDaysAgo = Instant.now().minus(7, ChronoUnit.DAYS);
        if (budgetRepo.existsByPurposeIgnoreCaseAndDateCreatedAfter(dto.getPurpose(), sevenDaysAgo)) {
            throw new IllegalArgumentException("Duplicate purpose within 7 days");
        }

        BudgetRequest br = new BudgetRequest();
        br.setDepartment(dept);
        br.setRequestedAmount(dto.getRequestedAmount());
        br.setPurpose(dto.getPurpose());
        br.setRequestedBy(dto.getRequestedBy());
        br.setStatus("PENDING");

        BudgetRequest saved = budgetRepo.save(br);

        
        AuditLog log = new AuditLog();
        log.setAction("CREATE");
        log.setEntityId(saved.getId());
        log.setEntityType("BudgetRequest");
        log.setChangedBy(dto.getRequestedBy());
        log.setNewValue("{\"status\":\"PENDING\",\"amount\":\"" + dto.getRequestedAmount() + "\"}");
        auditRepo.save(log);

        return saved;
    }

    @Transactional
    public BudgetRequest approve(Long requestId, Long managerId) {
        Manager mgr = requireManager(managerId);
        BudgetRequest br = budgetRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("BudgetRequest not found"));

        if (!"PENDING".equalsIgnoreCase(br.getStatus())) {
            throw new IllegalStateException("Only PENDING requests can be approved");
        }

        Department dept = br.getDepartment();

        
        BigDecimal oldBudget = dept.getCurrentBudget();
        dept.setCurrentBudget(oldBudget.subtract(br.getRequestedAmount()).max(BigDecimal.ZERO)); 
        
        String oldVal = "{\"status\":\"" + br.getStatus() + "\"}";
        br.setStatus("APPROVED");
        br.setApprovedBy(mgr.getName());

        
        deptRepo.save(dept);
        BudgetRequest saved = budgetRepo.save(br);

        
        AuditLog log = new AuditLog();
        log.setAction("APPROVE");
        log.setEntityId(saved.getId());
        log.setEntityType("BudgetRequest");
        log.setChangedBy(mgr.getName());
        log.setOldValue(oldVal);
        log.setNewValue("{\"status\":\"APPROVED\"}");
        auditRepo.save(log);

        return saved;
    }

    @Transactional
    public BudgetRequest reject(Long requestId, Long managerId) {
        Manager mgr = requireManager(managerId);
        BudgetRequest br = budgetRepo.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("BudgetRequest not found"));

        if (!"PENDING".equalsIgnoreCase(br.getStatus())) {
            throw new IllegalStateException("Only PENDING requests can be rejected");
        }

        String oldVal = "{\"status\":\"" + br.getStatus() + "\"}";
        br.setStatus("REJECTED");
        br.setApprovedBy(mgr.getName());
        BudgetRequest saved = budgetRepo.save(br);

        
        AuditLog log = new AuditLog();
        log.setAction("REJECT");
        log.setEntityId(saved.getId());
        log.setEntityType("BudgetRequest");
        log.setChangedBy(mgr.getName());
        log.setOldValue(oldVal);
        log.setNewValue("{\"status\":\"REJECTED\"}");
        auditRepo.save(log);

        return saved;
    }

    public List<BudgetRequest> listPending() { return budgetRepo.findByStatus("PENDING"); }

    private Manager requireManager(Long id) {
        Manager mgr = managerRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Manager not found"));
        if (!"MANAGER".equalsIgnoreCase(mgr.getRole())) {
            throw new SecurityException("Only MANAGER can approve/reject");
        }
        return mgr;
    }
}
