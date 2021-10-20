package com.example.demo.repository.jpa;

import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DepartmentRepositoryJpa extends DepartmentRepository, JpaRepository<Department, Long> {
}
