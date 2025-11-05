package com.example.hello_ws.endpoint;

import com.example.hello.Address;
import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SOAP Endpoint for HelloWorld service
 * Handles sayHello SOAP operations
 */
@Endpoint
public class HelloWorldEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldEndpoint.class);
    private static final String NAMESPACE_URI = "http://example.com/hello";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Handles sayHello SOAP request
     * 
     * @param request The SOAP request containing name, city, and optional datetime
     * @return SayHelloResponse with greeting message and address details
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sayHelloRequest")
    @ResponsePayload
    public SayHelloResponse sayHello(@RequestPayload SayHelloRequest request) {
        logger.info("Received sayHello request for: {} from {}", request.getName(), request.getCity());
        
        SayHelloResponse response = new SayHelloResponse();
        
        // Extract request parameters
        String name = request.getName();
        String city = request.getCity();
        String datetime = request.getDatetime();

        // Use current datetime if not provided
        if (datetime == null || datetime.isBlank()) {
            datetime = LocalDateTime.now().format(formatter);
        }

        // Build greeting message
        String greeting = String.format("Hello %s from %s! Current date/time: %s", 
                                       name, city, datetime);
        response.setGreeting(greeting);

        // Create and populate Address object
        Address address = new Address();
        address.setStreet("123 Main Street");
        address.setCity(city);
        address.setState("Maharashtra");
        address.setCountry("India");

        // Attach address to response
        response.setAddress(address);

        logger.info("Sending response: {}", greeting);
        
        return response;
    }
}
