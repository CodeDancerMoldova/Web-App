package com.example.demo.repositories;


import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.properties")
public class DepartmentRepositoryTest {

    @Autowired
    DepartmentRepository departmentRepository;


    @Test
    void testFindAll() {
        departmentRepository.save(DEPARTMENT_SECURITY_70);
        List<Department> employeeDtoList = departmentRepository.findAll();
        assertThat(employeeDtoList.size()).isEqualTo(1);
    }

    @Test
    void testFindById() {
        Department department = departmentRepository.save(DEPARTMENT_SECURITY_70);
        Department department1 = departmentRepository.findById(70L).orElse(null);
        assertThat(department1).isNotNull();
        assertThat(department1).isEqualTo(department);
    }


    @Test
    void testSave() {
        Department department = departmentRepository.save(DEPARTMENT_SECURITY_70);
        assertThat(department.getId()).isNotNull();
        assertThat(department).isEqualTo(DEPARTMENT_SECURITY_70);
    }


    @Test
    void testUpdate() {
        Department department = departmentRepository.save(DEPARTMENT_SECURITY_70);
        Department departmentToUpdate = departmentRepository.findById(70L).orElse(null);
        departmentToUpdate.setLocation("Chisinau");
        Department updated = departmentRepository.save(departmentToUpdate);
        assertThat("Chisinau").isEqualTo(updated.getLocation());
    }
}
