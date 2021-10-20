package com.example.demo.entity;


import com.example.demo.dto.EmployeeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id")
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email", unique = true)
    private String email;
    @Column(name = "phone_number", unique = true)
    private String phoneNumber;
    @Column(name = "salary")
    private Long salary;
    @Column(name = "department_id")
    private Long departmentId;

    public static Employee from(EmployeeDto employeeDto) {
        return Employee.builder()
                .id(employeeDto.getId())
                .departmentId(employeeDto.getDepartmentId())
                .email(employeeDto.getEmail())
                .lastName(employeeDto.getLastName())
                .firstName(employeeDto.getFirstName())
                .phoneNumber(employeeDto.getPhoneNumber())
                .salary(employeeDto.getSalary())
                .build();
    }
}
