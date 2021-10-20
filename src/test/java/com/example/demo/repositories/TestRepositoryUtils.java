package com.example.demo.repositories;

import com.example.demo.dto.DepartmentDto;
import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Department;
import com.example.demo.entity.Employee;

public class TestRepositoryUtils {

    public static final Employee ALEX_ID_5 = Employee.builder()
            .id(5L)
            .departmentId(60L)
            .email("david@gmail.com")
            .firstName("Alex")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            build();
    public static final EmployeeDto ALEX_ID_5_DTO = EmployeeDto.builder()
            .id(5L)
            .departmentId(60L)
            .email("david@gmail.com")
            .firstName("Alex")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            build();


    public static final DepartmentDto DEPARTMENT_SECURITY_70_DTO = DepartmentDto.builder()
            .id(70L)
            .location("Balti")
            .name("Security")
            .build();

    public static final Department DEPARTMENT_SECURITY_70 = Department.builder()
            .id(70L)
            .location("Balti")
            .name("Security")
            .build();

    public static final Department BAD_REQUEST_DEPARTMENT_SECURITY_70 = Department.builder()
            .location("")
            .name("Security")
            .build();

    public static final Employee BAD_REQUEST_ALEX_ID_5 = Employee.builder()
            .id(5L)
            .departmentId(60L)
            .email("")
            .firstName("")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            build();

    public static final DepartmentDto DEPARTMENT_DTO_UPDATE = DepartmentDto.builder()
            .location("Chisinau")
            .name("Administration")
            .build();
    public static final DepartmentDto DEPARTMENT_DTO_FULL = DepartmentDto.builder()
            .id(70L)
            .location("Chisinau")
            .name("Administration")
                    .build();
    public static final Department DEPARTMENT_UPDATE = Department.builder()
            .location("Chisinau")
            .name("Administration")
            .build();

    public static final EmployeeDto EMPLOYEE_UPDATED_DTO = EmployeeDto.builder()
            .id(5L)
            .email("andrei@gmail.com")
            .firstName("Andrei")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            departmentId(60L).
            build();

    public static final EmployeeDto EMPLOYEE_UPDATE_DTO = EmployeeDto.builder()
            .email("andrei@gmail.com")
            .firstName("Andrei")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            departmentId(60L)
            .build();

    public static final Employee EMPLOYEE_UPDATED= Employee.builder()
            .departmentId(60L)
            .email("andrei@gmail.com")
            .firstName("Andrei")
            .lastName("Gumaniuc")
            .salary(5000L)
            .phoneNumber("590.423.4569").
            build();

}