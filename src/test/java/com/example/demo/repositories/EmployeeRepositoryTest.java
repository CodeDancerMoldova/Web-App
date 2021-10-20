package com.example.demo.repositories;


import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class EmployeeRepositoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    void testFindAll() {
        employeeRepository.save(ALEX_ID_5);
        List<Employee> employeeDtoList = employeeRepository.findAll();
        assertThat(employeeDtoList.size()).isEqualTo(1);
    }

    @Test
    void testFindById() {
        Employee employee = employeeRepository.save(ALEX_ID_5);
        Employee employee1 = employeeRepository.findById(5L).orElse(null);
        assertThat(employee1).isNotNull();
        assertThat(employee1).isEqualTo(employee);
    }


    @Test
    void testSave() {
        Employee employee = employeeRepository.save(ALEX_ID_5);

        assertThat(employee.getId()).isNotNull();
        assertThat(employee).isEqualTo(ALEX_ID_5);
    }


    @Test
    void testUpdate() {
        Employee employee = employeeRepository.save(ALEX_ID_5);
        Employee employeeToUpdate = employeeRepository.findById(5L).orElse(null);
        employeeToUpdate.setEmail("gumaniuc@gmail.com");
        Employee updated = employeeRepository.save(employeeToUpdate);
        assertThat("gumaniuc@gmail.com").isEqualTo(updated.getEmail());
    }

}
