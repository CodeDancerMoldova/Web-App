package com.example.demo.dto;

import com.example.demo.entity.Department;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDto {

    private Long id;
    @NotNull
    @NotBlank
    @NotEmpty
    private String name;
    @NotNull
    @NotBlank
    @NotEmpty
    private String location;

    public static DepartmentDto from(Department department) {
        return DepartmentDto.builder()
                .id(department.getId())
                .location(department.getLocation())
                .name(department.getName())
                .build();
    }
}
