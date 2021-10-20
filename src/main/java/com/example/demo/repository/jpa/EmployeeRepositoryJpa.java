package com.example.demo.repository.jpa;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepositoryJpa extends EmployeeRepository, JpaRepository<Employee, Long> {

}
