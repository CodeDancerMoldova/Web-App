package com.example.demo.services;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.entity.Department;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.repository.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.demo.dto.DepartmentDto.from;

@AllArgsConstructor
@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<DepartmentDto> findAll() {
        return departmentRepository.findAll().stream().map(DepartmentDto::from).collect(Collectors.toList());
    }

    public DepartmentDto getDepartment(Long id) {
        Department department = departmentRepository.findById(id).orElseThrow(()->
                new DepartmentNotFoundException("Department with " + id + " not found"));
        return from(department);
    }

    public DepartmentDto save(DepartmentDto departmentDto) {
        Optional<Department> department = departmentRepository.findById(departmentDto.getId());
        if (department.isPresent()) {
            throw new IllegalStateException("Department already exists");
        }
        return from(departmentRepository.save(Department.from(departmentDto)));
    }


    public void deleteById(Long id) {
        boolean existsById = departmentRepository.existsById(id);
        if (!existsById) {
            throw new DepartmentNotFoundException("Department does not exist");
        }
        departmentRepository.deleteById(id);
    }

    public DepartmentDto update(Long id, DepartmentDto departmentDto) {
        Department departmentFound = departmentRepository.findById(id).orElseThrow(() ->
                new DepartmentNotFoundException("Department with id " + id + "does not exist"));
        departmentFound.setLocation(departmentDto.getLocation());
        departmentFound.setName(departmentDto.getName());
        return from(departmentRepository.save(departmentFound));
    }
}
