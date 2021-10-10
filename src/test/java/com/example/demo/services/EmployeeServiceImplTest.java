package com.example.demo.services;


import com.example.demo.dto.EmployeeDto;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5;
import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5_DTO;
import static com.example.demo.repositories.TestRepositoryUtils.EMPLOYEE_UPDATED;
import static com.example.demo.repositories.TestRepositoryUtils.EMPLOYEE_UPDATED_DTO;
import static com.example.demo.services.TestUtils.EMPLOYEE_LIST;
import static com.example.demo.services.TestUtils.EMPLOYEE_LIST_DTO;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeService employeeService;


    @Test
    void testFindAll() {
        when(employeeRepository.findAll()).thenReturn(EMPLOYEE_LIST);
        List<EmployeeDto> list = employeeService.findAll();
        verify(employeeRepository).findAll();
        assertThat(list).isEqualTo(EMPLOYEE_LIST_DTO);
    }

    @Test
    void testFindById() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.ofNullable(ALEX_ID_5));
        EmployeeDto employeeDto = employeeService.findById(5L);
        verify(employeeRepository).findById(5L);
        assertThat(employeeDto).isEqualTo(ALEX_ID_5_DTO);
    }

    @Test
    void testSave() {
        given(employeeRepository.save(ALEX_ID_5))
                .willReturn(ALEX_ID_5);
        assertThat(employeeService.save(ALEX_ID_5_DTO)).isEqualTo(ALEX_ID_5_DTO);
        verify(employeeRepository).save(ALEX_ID_5);
    }

    @Test
    void testUpdate() {
        when(employeeRepository.findById(5L)).thenReturn(Optional.ofNullable(ALEX_ID_5));
        when(employeeRepository.save(ALEX_ID_5)).thenReturn(EMPLOYEE_UPDATED);
        EmployeeDto employeeDto = employeeService.update(5L, EMPLOYEE_UPDATED_DTO);
        verify(employeeRepository).findById(5L);
        assertAll(
                () -> assertThat(ALEX_ID_5.getFirstName()).isEqualTo(employeeDto.getFirstName()),
                () -> assertThat(ALEX_ID_5.getLastName()).isEqualTo(employeeDto.getLastName()),
                () -> assertThat(ALEX_ID_5.getSalary()).isEqualTo(employeeDto.getSalary()),
                () -> assertThat(ALEX_ID_5.getDepartmentId()).isEqualTo(employeeDto.getDepartmentId()),
                () -> assertThat(ALEX_ID_5.getPhoneNumber()).isEqualTo(employeeDto.getPhoneNumber()),
                () -> assertThat(ALEX_ID_5.getEmail()).isEqualTo(employeeDto.getEmail())
        );
    }

}
