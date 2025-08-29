package com.demo.budget_approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.budget_approval.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Long> { 
	
}
