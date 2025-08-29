package com.demo.budget_approval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.budget_approval.entity.Department;
import com.demo.budget_approval.entity.Manager;
import com.demo.budget_approval.repository.DepartmentRepository;
import com.demo.budget_approval.repository.ManagerRepository;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    
	
	
	@Autowired
	private DepartmentRepository deptRepo;
	@Autowired
	private ManagerRepository mgrRepo;

    public AdminController(DepartmentRepository deptRepo, ManagerRepository mgrRepo) {
        this.deptRepo = deptRepo; this.mgrRepo = mgrRepo;
    }

    // POST ts
    @PostMapping("/departments")
    public ResponseEntity<Department> createDept(@RequestBody Department d) {
        if (d.getCurrentBudget() == null) d.setCurrentBudget(BigDecimal.ZERO);
        return ResponseEntity.ok(deptRepo.save(d));
    }

    // GET s
    @GetMapping("/departments")
    public List<Department> listDepts() { return deptRepo.findAll(); }

    // POST 
    @PostMapping("/managers")
    public ResponseEntity<Manager> createManager(@RequestBody Manager m) {
        if (m.getRole() == null || m.getRole().isBlank()) m.setRole("MANAGER");
        return ResponseEntity.ok(mgrRepo.save(m));
    }

    // GET 
    @GetMapping("/managers")
    public List<Manager> listManagers() { return mgrRepo.findAll(); }
}
