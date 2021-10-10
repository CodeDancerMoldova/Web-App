package com.example.demo.controllers;


import com.example.demo.controller.DepartmentController;
import com.example.demo.exception.DepartmentNotFoundException;
import com.example.demo.services.DepartmentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;

import static com.example.demo.repositories.TestRepositoryUtils.BAD_REQUEST_DEPARTMENT_SECURITY_70;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_DTO_UPDATE;
import static com.example.demo.repositories.TestRepositoryUtils.DEPARTMENT_SECURITY_70_DTO;
import static com.example.demo.services.TestUtils.DEPARTMENT_LIST_dto;
import static com.example.demo.services.TestUtils.TYPE_JSON;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = DepartmentController.class)
public class DepartmentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @MockBean
    private DepartmentService departmentService;


    @Test
    void testRegisterWorksThroughAllLayers() throws Exception {
        given(departmentService.save(DEPARTMENT_SECURITY_70_DTO)).willReturn(DEPARTMENT_SECURITY_70_DTO);
        MvcResult mvcResult = mockMvc.perform(post("/api/departments")
                        .content(objectMapper.writeValueAsString(DEPARTMENT_SECURITY_70_DTO))
                        .contentType("application/json"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_SECURITY_70_DTO));
        verify(departmentService).save(DEPARTMENT_SECURITY_70_DTO);
    }

    @Test
    void testRegistrationIsBadRequest() throws Exception {
        mockMvc.perform(post("/api/departments")
                        .content(objectMapper.writeValueAsString(BAD_REQUEST_DEPARTMENT_SECURITY_70))
                        .contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }


    @Test
    void testFindAllDepartments() throws Exception {
        given(departmentService.findAll()).willReturn(DEPARTMENT_LIST_dto);
        MvcResult mvcResult = mockMvc.perform(get("/api/departments")
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String actualResponse = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        verify(departmentService).findAll();
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_LIST_dto));
    }


    @Test
    void testFindById() throws Exception {
        given(departmentService.getDepartment(70L)).willReturn(DEPARTMENT_SECURITY_70_DTO);
        MvcResult mvcResult = mockMvc.perform(get("/api/departments/70")
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        verify(departmentService).getDepartment(70L);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_SECURITY_70_DTO));
    }

    @Test
    void testFindById_ThrowNotFound() throws Exception {
        given(departmentService.getDepartment(anyLong()))
                .willThrow(new DepartmentNotFoundException("NotFound"));

        mockMvc.perform(get("/api/departments/{id}", anyLong())
                        .contentType(TYPE_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(DepartmentNotFoundException.class));
    }


    @Test
    void testUpdateDepartment() throws Exception {
        given(departmentService.update(70L, DEPARTMENT_DTO_UPDATE))
                .willReturn(DEPARTMENT_DTO_UPDATE);

        MvcResult mvcResult = mockMvc.perform(put("/api/departments/{id}", 70L)
                        .content(objectMapper.writeValueAsString(DEPARTMENT_DTO_UPDATE))
                        .contentType(TYPE_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String actualResponse = mvcResult.getResponse().getContentAsString();
        verify(departmentService).update(70L, DEPARTMENT_DTO_UPDATE);
        assertThat(actualResponse).isEqualToIgnoringWhitespace(objectMapper.writeValueAsString(DEPARTMENT_DTO_UPDATE));
    }

    @Test
    void testUpdateDepartment_BadRequest() throws Exception {
        mockMvc.perform(put("/api/departments/{id}", 70L)
                        .content(objectMapper.writeValueAsString(BAD_REQUEST_DEPARTMENT_SECURITY_70))
                        .contentType(TYPE_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void testUpdateDepartment_NotFound() throws Exception {

        given(departmentService.update(60L, DEPARTMENT_DTO_UPDATE))
                .willThrow(new DepartmentNotFoundException("NotFound"));

        mockMvc.perform(put("/api/departments/{id}", 60L)
                        .content(objectMapper.writeValueAsString(DEPARTMENT_DTO_UPDATE))
                        .contentType(TYPE_JSON))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertThat(result.getResolvedException())
                        .isInstanceOfAny(DepartmentNotFoundException.class));
    }

}
