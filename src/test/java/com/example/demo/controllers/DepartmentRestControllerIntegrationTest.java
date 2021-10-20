package com.example.demo.controllers;


import com.example.demo.dto.DepartmentDto;
import com.example.demo.entity.Department;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.services.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;

import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_DTO_FULL;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_DTO_UPDATE;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70_DTO;
import static com.example.demo.services.TestUtils.DEPARTMENT_LIST;
import static com.example.demo.services.TestUtils.DEPARTMENT_LIST_dto;
import static com.example.demo.services.TestUtils.TYPE_JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@Transactional
public class DepartmentRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;


    @Test
    void testFindAllDepartments() throws Exception {
        for (Department dep : DEPARTMENT_LIST) {
            departmentRepository.save(dep);
        }
        MvcResult mvcResult = mockMvc.perform(get("/api/departments")
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_LIST_dto));
    }


    @Test
    void testFindById() throws Exception {
        departmentRepository.save(DEPARTMENT_SECURITY_70);
        MvcResult mvcResult = mockMvc.perform(get("/api/departments/{id}", 70)
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_SECURITY_70_DTO));

    }


    @Test
    void testRegisterWorksThroughAllLayers() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/api/departments")
                        .content(objectMapper.writeValueAsString(DEPARTMENT_DTO_FULL))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        DepartmentDto actualResponse = objectMapper.readValue(mvcResult.getResponse().
                getContentAsString(), DepartmentDto.class);

        assertThat(actualResponse).isEqualTo(DEPARTMENT_DTO_FULL);
        assertThat(actualResponse).isEqualTo(departmentService.getDepartment(actualResponse.getId()));
    }

    @Test
    void testUpdateDepartment() throws Exception {
        departmentRepository.save(DEPARTMENT_SECURITY_70);

        MvcResult mvcResult = mockMvc.perform(put("/api/departments/{id}", 70L)
                        .content(objectMapper.writeValueAsString(DEPARTMENT_DTO_UPDATE))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        DepartmentDto actualResponse = objectMapper.readValue(mvcResult.getResponse().
                getContentAsString(), DepartmentDto.class);

        assertThat(actualResponse).isEqualTo(DEPARTMENT_DTO_FULL);
    }

}
