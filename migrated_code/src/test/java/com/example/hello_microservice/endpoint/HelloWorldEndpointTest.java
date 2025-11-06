package com.example.hello_microservice.endpoint;

import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.ws.test.server.MockWebServiceClient;
import org.springframework.xml.transform.StringSource;

import static org.springframework.ws.test.server.RequestCreators.withPayload;
import static org.springframework.ws.test.server.ResponseMatchers.*;

/**
 * Integration tests for SOAP endpoint using spring-ws-test.
 * Tests the full SOAP request/response cycle.
 */
@SpringBootTest
@DisplayName("SOAP Endpoint Integration Tests")
class HelloWorldEndpointTest {

    @Autowired
    private ApplicationContext applicationContext;

    private MockWebServiceClient mockClient;

    @BeforeEach
    void setUp() {
        mockClient = MockWebServiceClient.createClient(applicationContext);
    }

    @Test
    @DisplayName("Should handle SOAP request successfully")
    void testSoapEndpoint_ValidRequest() {
        // Given
        String request = """
                <sayHelloRequest xmlns="http://example.com/hello">
                    <name>John Doe</name>
                    <city>Mumbai</city>
                    <datetime>2025-11-06T10:00:00</datetime>
                </sayHelloRequest>
                """;

        // When/Then
        mockClient
            .sendRequest(withPayload(new StringSource(request)))
            .andExpect(noFault())
            .andExpect(validPayload(applicationContext.getResource("classpath:wsdl/hello.xsd")))
            .andExpect(xpath("//*[local-name()='greeting']").exists())
            .andExpect(xpath("//*[local-name()='greeting']").string().contains("Hello John Doe"))
            .andExpect(xpath("//*[local-name()='city']").string().equals("Mumbai"));
    }

    @Test
    @DisplayName("Should handle request without datetime")
    void testSoapEndpoint_WithoutDatetime() {
        // Given
        String request = """
                <sayHelloRequest xmlns="http://example.com/hello">
                    <name>Jane Smith</name>
                    <city>Delhi</city>
                </sayHelloRequest>
                """;

        // When/Then
        mockClient
            .sendRequest(withPayload(new StringSource(request)))
            .andExpect(noFault())
            .andExpect(xpath("//*[local-name()='greeting']").exists())
            .andExpect(xpath("//*[local-name()='greeting']").string().contains("Jane Smith"));
    }
}
