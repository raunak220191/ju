package com.example.hello_microservice.service;

import com.example.hello.Address;
import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for HelloService.
 * Uses JUnit 5 and AssertJ for fluent assertions.
 */
@DisplayName("HelloService Unit Tests")
class HelloServiceTest {

    private HelloService helloService;

    @BeforeEach
    void setUp() {
        helloService = new HelloService();
    }

    @Test
    @DisplayName("Should process hello request with all fields provided")
    void testProcessHelloRequest_WithAllFields() {
        // Given
        SayHelloRequest request = new SayHelloRequest();
        request.setName("John");
        request.setCity("Mumbai");
        request.setDatetime("2025-11-06T10:00:00");

        // When
        SayHelloResponse response = helloService.processHelloRequest(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getGreeting())
            .contains("Hello John")
            .contains("Mumbai")
            .contains("2025-11-06T10:00:00");
        
        Address address = response.getAddress();
        assertThat(address).isNotNull();
        assertThat(address.getCity()).isEqualTo("Mumbai");
        assertThat(address.getCountry()).isEqualTo("India");
        assertThat(address.getStreet()).isEqualTo("123 Main Street");
    }

    @Test
    @DisplayName("Should use current datetime when not provided")
    void testProcessHelloRequest_WithoutDatetime() {
        // Given
        SayHelloRequest request = new SayHelloRequest();
        request.setName("Jane");
        request.setCity("Delhi");

        // When
        SayHelloResponse response = helloService.processHelloRequest(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getGreeting())
            .contains("Hello Jane")
            .contains("Delhi");
        assertThat(response.getAddress()).isNotNull();
    }

    @Test
    @DisplayName("Should handle different cities correctly")
    void testProcessHelloRequest_DifferentCities() {
        // Given
        SayHelloRequest request = new SayHelloRequest();
        request.setName("Test User");
        request.setCity("Bangalore");
        request.setDatetime("2025-11-06");

        // When
        SayHelloResponse response = helloService.processHelloRequest(request);

        // Then
        assertThat(response.getAddress().getCity()).isEqualTo("Bangalore");
        assertThat(response.getGreeting()).contains("Bangalore");
    }
}
