package com.example.demo.controller;


import com.example.demo.dto.DepartmentDto;
import com.example.demo.services.DepartmentService;
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
@RequestMapping(path = "/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping
    public List<DepartmentDto> getDepartments() {
        return departmentService.findAll();
    }

    @GetMapping("/{id}")
    public DepartmentDto getDepartment(@PathVariable("id") Long id) {
        return departmentService.getDepartment(id);
    }

    @PostMapping
    public DepartmentDto registerNewDepartment(@Valid @RequestBody DepartmentDto departmentDto) {
        return departmentService.save(departmentDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDepartment(@PathVariable("id") Long id) {
        departmentService.deleteById(id);
    }

    @PutMapping("/{id}")
    public DepartmentDto updateDepartment(@PathVariable("id") Long id,
                                          @Valid @RequestBody DepartmentDto departmentDto) {
        return departmentService.update(id, departmentDto);
    }
}
