package com.example.demo.entity;

import com.example.demo.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "department")
public class Department {

    @Id
    private Long id;

    private String name;

    private String location;

    public  static Department  from(DepartmentDto departmentDto) {
        return Department.builder()
                .location(departmentDto.getLocation())
                .name(departmentDto.getName())
                .id(departmentDto.getId())
                .build();
    }
}
