package com.example.hello_ws.endpoint;

import com.example.hello.Address;
import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldEndpointTest {

    private HelloWorldEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new HelloWorldEndpoint();
    }

    @Test
    void testSayHello() {
        // Arrange
        SayHelloRequest request = new SayHelloRequest();
        request.setName("John Doe");
        request.setCity("Mumbai");
        request.setDatetime("2025-11-05 10:00:00");

        // Act
        SayHelloResponse response = endpoint.sayHello(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getGreeting());
        assertTrue(response.getGreeting().contains("John Doe"));
        assertTrue(response.getGreeting().contains("Mumbai"));
        
        Address address = response.getAddress();
        assertNotNull(address);
        assertEquals("Mumbai", address.getCity());
        assertEquals("Maharashtra", address.getState());
        assertEquals("India", address.getCountry());
    }

    @Test
    void testSayHelloWithoutDatetime() {
        // Arrange
        SayHelloRequest request = new SayHelloRequest();
        request.setName("Jane Smith");
        request.setCity("Pune");

        // Act
        SayHelloResponse response = endpoint.sayHello(request);

        // Assert
        assertNotNull(response);
        assertNotNull(response.getGreeting());
        assertTrue(response.getGreeting().contains("Jane Smith"));
        assertTrue(response.getGreeting().contains("Pune"));
    }
}
