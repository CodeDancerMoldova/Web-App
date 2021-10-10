package com.example.demo.controller;

import com.example.demo.dto.EmployeeDto;
import com.example.demo.services.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeDto> getEmployees() {
        return employeeService.findAll();
    }

    @GetMapping("/{id}")
    public EmployeeDto getEmployee(@PathVariable("id") Long id) {
        return employeeService.findById(id);
    }

    @PostMapping
    public EmployeeDto registerNewEmployee(@RequestBody @Valid EmployeeDto employeeDto) {
        return employeeService.save(employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable("id") Long id) {
        employeeService.deleteById(id);
    }

    @PutMapping("/{id}")
    public EmployeeDto updateEmployee(@PathVariable("id") Long id, @Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.update(id, employeeDto);

    }
}
