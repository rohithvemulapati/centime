package com.centime.data_access.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.centime.data_access.dto.HierarchyResponse;
import com.centime.data_access.service.DataAccessService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = DataAccessController.class)
class DataAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DataAccessService dataAccessService;

    @Test
    void testGetFullHierarchy_ShouldReturnList() throws Exception {

    	HierarchyResponse node1 = new HierarchyResponse("Node1");
        HierarchyResponse node2 = new HierarchyResponse("Node2");
        List<HierarchyResponse> hierarchyList = Arrays.asList(node1, node2);

        when(dataAccessService.getAllHierarchy()).thenReturn(hierarchyList);

        mockMvc.perform(get("/api/v1/hierarchy/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(hierarchyList)));
    }

    @Test
    void testGetHierarchyById_ShouldReturnNode() throws Exception {

    	HierarchyResponse node = new HierarchyResponse("Node1");
        when(dataAccessService.getHierarchyById(1)).thenReturn(node);

        mockMvc.perform(get("/api/v1/hierarchy/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(node)));
    }

    @Test
    void testGetFullHierarchy_ShouldThrowException() throws Exception {

    	when(dataAccessService.getAllHierarchy()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/api/v1/hierarchy/list")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void testGetHierarchyById_ShouldThrowException() throws Exception {

    	when(dataAccessService.getHierarchyById(99)).thenThrow(new RuntimeException("Not Found"));

        mockMvc.perform(get("/api/v1/hierarchy/99")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError());
    }
}
