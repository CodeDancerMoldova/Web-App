package com.example.demo.controllers;


import com.example.demo.dto.EmployeeDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5;
import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5_DTO;
import static com.example.demo.repositories.TestRepositoryUtils.EMPLOYEE_UPDATED_DTO;
import static com.example.demo.repositories.TestRepositoryUtils.EMPLOYEE_UPDATE_DTO;
import static com.example.demo.services.TestUtils.EMPLOYEE_LIST;
import static com.example.demo.services.TestUtils.EMPLOYEE_LIST_DTO;
import static com.example.demo.services.TestUtils.TYPE_JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc

public class EmployeeRestControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Test
    void testRegisterWorksThroughAllLayers() throws Exception {
        employeeRepository.save(ALEX_ID_5);
        MvcResult mvcResult = mockMvc.perform(post("/api/employees")
                        .content(objectMapper.writeValueAsString(ALEX_ID_5_DTO))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        EmployeeDto actualResponse = objectMapper.readValue(mvcResult.getResponse().
                getContentAsString(), EmployeeDto.class);

        assertThat(actualResponse).isEqualTo(ALEX_ID_5_DTO);
        assertThat(actualResponse).isEqualTo(employeeService.findById(actualResponse.getId()));
    }


    @Test
    void testFindById() throws Exception {
        employeeRepository.save(ALEX_ID_5);
        MvcResult mvcResult = mockMvc.perform(get("/api/employees/{id}", 5)
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(ALEX_ID_5_DTO));

    }

    @Test
    void testFindAllDepartments() throws Exception {
        for (Employee dep : EMPLOYEE_LIST) {
            employeeRepository.save(dep);
        }
        MvcResult mvcResult = mockMvc.perform(get("/api/employees")
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(EMPLOYEE_LIST_DTO));
    }

    @Test
    void testUpdateDepartment() throws Exception {
        employeeRepository.save(ALEX_ID_5);

        MvcResult mvcResult = mockMvc.perform(put("/api/employees/{id}", 5L)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_UPDATE_DTO))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        EmployeeDto actualResponse = objectMapper.readValue(mvcResult.getResponse().
                getContentAsString(), EmployeeDto.class);

        assertThat(actualResponse).isEqualTo(EMPLOYEE_UPDATED_DTO);
    }

}
