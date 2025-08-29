package com.demo.budget_approval.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BigDecimal currentBudget = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal yearlyAllocation = BigDecimal.ZERO;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getCurrentBudget() {
		return currentBudget;
	}

	public void setCurrentBudget(BigDecimal currentBudget) {
		this.currentBudget = currentBudget;
	}

	public BigDecimal getYearlyAllocation() {
		return yearlyAllocation;
	}

	public void setYearlyAllocation(BigDecimal yearlyAllocation) {
		this.yearlyAllocation = yearlyAllocation;
	}

    
}
