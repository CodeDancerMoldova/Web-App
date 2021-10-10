package com.example.demo.services;


import com.example.demo.dto.DepartmentDto;
import com.example.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_DTO_UPDATE;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70_DTO;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_UPDATE;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DepartmentServiceImplTest {

    @Mock
    DepartmentRepository departmentRepository;

    @InjectMocks
    DepartmentService departmentService;

    @Test
    void testFindAll() {
        when(departmentRepository.findAll()).thenReturn(TestUtils.DEPARTMENT_LIST);
        List<DepartmentDto> list = departmentService.findAll();
        verify(departmentRepository).findAll();
        assertThat(list).isEqualTo(TestUtils.DEPARTMENT_LIST_dto);
    }

    @Test
    void testFindById() {
        given(departmentRepository.findById(70L))
                .willReturn(Optional.ofNullable(DEPARTMENT_SECURITY_70));
        DepartmentDto departmentDto = departmentService.getDepartment(70L);
        verify(departmentRepository).findById(70L);
        assertThat(departmentDto).isEqualTo(DEPARTMENT_SECURITY_70_DTO);
    }

    @Test
    void testSave() {
        given(departmentRepository.save(DEPARTMENT_SECURITY_70))
                .willReturn(DEPARTMENT_SECURITY_70);
        assertThat(departmentService.save(DEPARTMENT_SECURITY_70_DTO)).isEqualTo(DEPARTMENT_SECURITY_70_DTO);
        verify(departmentRepository).save(DEPARTMENT_SECURITY_70);
    }

    @Test
    void testUpdate() {
        when(departmentRepository.findById(70L)).thenReturn(Optional.ofNullable(DEPARTMENT_SECURITY_70));
        when(departmentRepository.save(DEPARTMENT_SECURITY_70)).thenReturn(DEPARTMENT_UPDATE);
        DepartmentDto departmentDto = departmentService.update(70L, DEPARTMENT_DTO_UPDATE);
        verify(departmentRepository).findById(70L);
        assertAll(
                () -> assertThat(DEPARTMENT_SECURITY_70.getLocation()).isEqualTo(departmentDto.getLocation()),
                () -> assertThat(DEPARTMENT_SECURITY_70.getName()).isEqualTo(departmentDto.getName())
        );
    }
}
