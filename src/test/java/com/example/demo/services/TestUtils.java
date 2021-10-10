package com.example.demo.services;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;

import java.util.List;

public class TestUtils {
    public final static String TYPE_JSON = "application/json";

    public static final List<Department> DEPARTMENT_LIST =
            List.of(Department.builder().
                            id(1L).
                            location("Ring")
                            .name("Administrations").build(),
                    Department.builder().
                            id(2L).
                            location("Ringo")
                            .name("AdministrationsGood").build());

    public static final List<DepartmentDto> DEPARTMENT_LIST_dto =
            List.of(DepartmentDto.builder().
                            id(1L).
                            location("Ring")
                            .name("Administrations").build(),
                    DepartmentDto.builder().
                            id(2L).
                            location("Ringo")
                            .name("AdministrationsGood").build());

    public static final List<Employee> EMPLOYEE_LIST =
            List.of(Employee.builder()
                            .id(1L)
                            .firstName("Daniel")
                            .lastName("Azerv")
                            .departmentId(70L)
                            .email("danu@gmail.com")
                            .salary(15000L)
                            .phoneNumber("500.700.900")
                            .build(),
                    Employee.builder()
                            .id(2L)
                            .firstName("Marcel")
                            .lastName("Azerb")
                            .departmentId(70L)
                            .email("marcel@gmail.com")
                            .salary(16000L)
                            .phoneNumber("500.700.901")
                            .build());
    public static final List<EmployeeDto> EMPLOYEE_LIST_DTO =
            List.of(EmployeeDto.builder()
                            .id(1L)
                            .firstName("Daniel")
                            .lastName("Azerv")
                            .departmentId(70L)
                            .email("danu@gmail.com")
                            .salary(15000L)
                            .phoneNumber("500.700.900")
                            .build(),
                    EmployeeDto.builder()
                            .id(2L)
                            .firstName("Marcel")
                            .lastName("Azerb")
                            .departmentId(70L)
                            .email("marcel@gmail.com")
                            .salary(16000L)
                            .phoneNumber("500.700.901")
                            .build());
}
