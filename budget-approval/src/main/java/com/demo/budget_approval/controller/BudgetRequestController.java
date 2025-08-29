package com.demo.budget_approval.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.demo.budget_approval.dto.BudgetRequestCreateDTO;
import com.demo.budget_approval.entity.BudgetRequest;
import com.demo.budget_approval.service.BudgetRequestService;

import java.util.List;

@RestController
@RequestMapping("/api/budget-request")
public class BudgetRequestController {

	@Autowired
    private BudgetRequestService service;
    
	public BudgetRequestController(BudgetRequestService service) { this.service = service; }

    // POST
    @PostMapping
    public ResponseEntity<BudgetRequest> create(@RequestBody BudgetRequestCreateDTO dto) {
        return ResponseEntity.ok(service.create(dto));
       
    }

    // PUT
    @PutMapping("/{id}/approve")
    public ResponseEntity<BudgetRequest> approve(@PathVariable Long id, @RequestParam Long managerId) {
        return ResponseEntity.ok(service.approve(id, managerId));
    }

    // PUT
    @PutMapping("/{id}/reject")
    public ResponseEntity<BudgetRequest> reject(@PathVariable Long id, @RequestParam Long managerId) {
        return ResponseEntity.ok(service.reject(id, managerId));
    }

    // GET
    @GetMapping("/pending")
    public ResponseEntity<List<BudgetRequest>> pending() {
        return ResponseEntity.ok(service.listPending());
    }
}
