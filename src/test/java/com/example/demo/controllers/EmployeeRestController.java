package com.example.demo.controllers;


import com.example.demo.controller.EmployeeController;
import com.example.demo.exception.EmployeeNotFoundException;
import com.example.demo.services.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static com.example.demo.repositories.TestRepositoryUtils.ALEX_ID_5_DTO;
import static com.example.demo.repositories.TestRepositoryUtils.BAD_REQUEST_ALEX_ID_5;
import static com.example.demo.repositories.TestRepositoryUtils.EMPLOYEE_UPDATED_DTO;
import static com.example.demo.services.TestUtils.EMPLOYEE_LIST_DTO;
import static com.example.demo.services.TestUtils.TYPE_JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = EmployeeController.class)
public class EmployeeRestController {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EmployeeService employeeService;


    @Test
    void testRegisterWorksThroughAllLayers() throws Exception {
        given(employeeService.save(ALEX_ID_5_DTO)).willReturn(ALEX_ID_5_DTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/employees")
                        .content(objectMapper.writeValueAsString(ALEX_ID_5_DTO))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(ALEX_ID_5_DTO));
        verify(employeeService).save(ALEX_ID_5_DTO);
    }

    @Test
    void testRegistrationIsBadRequest() throws Exception {
        mockMvc.perform(post("/api/employees")
                        .content(objectMapper.writeValueAsString(BAD_REQUEST_ALEX_ID_5))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testFindAllDepartments() throws Exception {
        given(employeeService.findAll()).willReturn(EMPLOYEE_LIST_DTO);
        MvcResult mvcResult = mockMvc.perform(get("/api/employees")
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        verify(employeeService).findAll();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(EMPLOYEE_LIST_DTO));
    }

    @Test
    void testFindById() throws Exception {
        given(employeeService.findById(5L)).willReturn(ALEX_ID_5_DTO);

        MvcResult mvcResult = mockMvc.perform(get("/api/employees/{id}", 5L)
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        verify(employeeService).findById(5L);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(ALEX_ID_5_DTO));
    }

    @Test
    void testFindById_ThrowNotFound() throws Exception {
        given(employeeService.findById(anyLong()))
                .willThrow(new EmployeeNotFoundException("NotFound"));

        mockMvc.perform(get("/api/employees/{id}", anyLong())
                        .contentType(TYPE_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(EmployeeNotFoundException.class));
    }

    @Test
    void testUpdateEmployee() throws Exception {
        given(employeeService.update(5L, EMPLOYEE_UPDATED_DTO))
                .willReturn(EMPLOYEE_UPDATED_DTO);

        MvcResult mvcResult = mockMvc.perform(put("/api/employees/{id}", 5L)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_UPDATED_DTO))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        verify(employeeService).update(5L, EMPLOYEE_UPDATED_DTO);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(EMPLOYEE_UPDATED_DTO));
    }

    @Test
    void testUpdateEmployee_BadRequest() throws Exception {
        mockMvc.perform(put("/api/employees/{id}", 5L)
                        .content(objectMapper.writeValueAsString(BAD_REQUEST_ALEX_ID_5))
                        .contentType(TYPE_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateDepartment_NotFound() throws Exception {

        given(employeeService.update(5L, EMPLOYEE_UPDATED_DTO))
                .willThrow(new EmployeeNotFoundException("NotFound"));

        mockMvc.perform(put("/api/employees/{id}", 5L)
                        .content(objectMapper.writeValueAsString(EMPLOYEE_UPDATED_DTO))
                        .contentType(TYPE_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(EmployeeNotFoundException.class));
    }
}
