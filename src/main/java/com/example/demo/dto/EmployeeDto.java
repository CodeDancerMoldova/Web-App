package com.example.demo.dto;

import com.example.demo.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String firstName;
    @NotNull
    @NotBlank
    @NotEmpty
    private String lastName;
    @NotNull
    @NotBlank
    @NotEmpty
    private String email;
    @NotNull
    @NotBlank
    @NotEmpty
    private String phoneNumber;
    @Min(1)
    private Long salary;
    private Long departmentId;

    public static EmployeeDto from(Employee employee) {
        return EmployeeDto.builder()
                .id(employee.getId())
                .departmentId(employee.getDepartmentId())
                .email(employee.getEmail())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .phoneNumber(employee.getPhoneNumber())
                .salary(employee.getSalary())
                .build();
    }


}
