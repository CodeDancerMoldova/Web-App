package com.example.demo.repository;

import com.example.demo.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository {
    List<Employee> findAll();

    Optional<Employee> findEmployeeByEmail(String email);

    boolean existsById(Long id);

    Optional<Employee> findById(Long id);

    void deleteById(Long id);

    Employee save(Employee employee);
}
