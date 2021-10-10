package com.example.demo.repository;

import com.example.demo.entity.Department;

import java.util.List;
import java.util.Optional;


public interface DepartmentRepository {
    List<Department> findAll();

    Optional<Department> findById(Long id);

    boolean existsById(Long id);

    void deleteById(Long id);

    Department save(Department department);
}
