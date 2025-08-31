package com.centime.service3.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import com.centime.service3.controllers.ServiceThreeController;
import com.centime.service3.dto.NameDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ServiceThreeController.class)
class ServiceThreeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testConcatNames_ShouldReturnFullName() throws Exception {
        // Arrange
        NameDTO nameDTO = new NameDTO();
        nameDTO.setName("John");
        nameDTO.setSurName("Doe");

        // Act & Assert
        mockMvc.perform(post("/api/v1/concat-names")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(nameDTO)))
                .andExpect(status().isOk())
                .andExpect(content().string("John Doe"));
    }

    @Test
    void testConcatNames_ShouldReturnBadRequest_WhenNameMissing() throws Exception {
        // Arrange: Missing 'name' field
        String requestBody = "{\"surName\":\"Doe\"}";

        // Act & Assert
        mockMvc.perform(post("/api/v1/concat-names")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isBadRequest());
    }
}
