package com.demo.budget_approval.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.budget_approval.entity.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByNameIgnoreCase(String name);
}
