package com.example.hello_microservice.controller;

import com.example.hello.SayHelloRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

/**
 * Integration tests for REST controller using MockMvc.
 * Tests the REST API endpoints with proper HTTP semantics.
 */
@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("REST Controller Integration Tests")
class HelloRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Should handle GET request with query parameters")
    void testGetHello_WithQueryParams() throws Exception {
        mockMvc.perform(get("/api/hello")
                .param("name", "John")
                .param("city", "Mumbai")
                .param("datetime", "2025-11-06T10:00:00"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.greeting", containsString("Hello John")))
            .andExpect(jsonPath("$.greeting", containsString("Mumbai")))
            .andExpect(jsonPath("$.address.city", is("Mumbai")))
            .andExpect(jsonPath("$.address.country", is("India")));
    }

    @Test
    @DisplayName("Should handle GET request without datetime")
    void testGetHello_WithoutDatetime() throws Exception {
        mockMvc.perform(get("/api/hello")
                .param("name", "Jane")
                .param("city", "Delhi"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.greeting", containsString("Jane")))
            .andExpect(jsonPath("$.address.city", is("Delhi")));
    }

    @Test
    @DisplayName("Should handle POST request with JSON body")
    void testPostHello_WithJsonBody() throws Exception {
        SayHelloRequest request = new SayHelloRequest();
        request.setName("Alice");
        request.setCity("Bangalore");
        request.setDatetime("2025-11-06");

        mockMvc.perform(post("/api/hello")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.greeting", containsString("Alice")))
            .andExpect(jsonPath("$.address.city", is("Bangalore")));
    }

    @Test
    @DisplayName("Should return 400 when required parameters are missing")
    void testGetHello_MissingParams() throws Exception {
        mockMvc.perform(get("/api/hello")
                .param("name", "John"))
            .andExpect(status().isBadRequest());
    }
}
