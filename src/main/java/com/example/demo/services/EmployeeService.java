package com.example.demo.services;


import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.dto.EmployeeDto.from;

@AllArgsConstructor
@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public List<EmployeeDto> findAll() {
        return employeeRepository.findAll().stream().map(EmployeeDto::from).collect(Collectors.toList());
    }

    public EmployeeDto save(EmployeeDto employeeDto) {
        return from(employeeRepository.save(Employee.from(employeeDto)));
    }

    public void deleteById(Long id) {
        boolean existsById = employeeRepository.existsById(id);
        if (!existsById) {
            throw new EmployeeNotFoundException("Employee with id " + id + " does not exists");
        }
        employeeRepository.deleteById(id);

    }

    public EmployeeDto findById(Long id) {
        Employee employee = employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee with id " + id + " does not exist"));
        return from(employee);
    }

    public EmployeeDto update(Long id, EmployeeDto employeeDto) {
        Employee employeeFound = employeeRepository.findById(id).orElseThrow(() ->
                new EmployeeNotFoundException("Employee with id " + id + "does not exist"));
        employeeFound.setSalary(employeeDto.getSalary());
        employeeFound.setFirstName(employeeDto.getFirstName());
        employeeFound.setLastName(employeeDto.getLastName());
        employeeFound.setEmail(employeeDto.getEmail());
        employeeFound.setDepartmentId(employeeDto.getDepartmentId());
        return from(employeeRepository.save(employeeFound));
    }
}
