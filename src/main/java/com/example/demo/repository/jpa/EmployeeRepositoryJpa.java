package com.example.demo.repository.jpa;

import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa")
public interface EmployeeRepositoryJpa extends EmployeeRepository, JpaRepository<Employee, Long> {

}
