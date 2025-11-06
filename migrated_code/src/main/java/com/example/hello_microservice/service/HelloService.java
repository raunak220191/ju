package com.example.hello_microservice.service;

import com.example.hello.Address;
import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Business logic service for Hello operations.
 * This service is used by both SOAP endpoint and REST controller.
 * Follows cloud-native patterns with proper logging and error handling.
 */
@Service
public class HelloService {

    private static final Logger logger = LoggerFactory.getLogger(HelloService.class);

    /**
     * Process hello request and generate response with greeting and address.
     * 
     * @param request The hello request containing name, city, and optional datetime
     * @return Response with greeting message and address details
     */
    public SayHelloResponse processHelloRequest(SayHelloRequest request) {
        logger.debug("Processing hello request for name: {}, city: {}", 
                     request.getName(), request.getCity());

        SayHelloResponse response = new SayHelloResponse();
        
        String name = request.getName();
        String city = request.getCity();
        String datetime = request.getDatetime();

        // Use current datetime if not provided
        if (datetime == null || datetime.isBlank()) {
            datetime = LocalDateTime.now().toString();
            logger.debug("No datetime provided, using current: {}", datetime);
        }

        // Build greeting message
        String greeting = String.format("Hello %s from %s! Current date/time: %s", 
                                       name, city, datetime);
        response.setGreeting(greeting);

        // Create and populate address
        Address address = createAddress(city);
        response.setAddress(address);

        logger.info("Successfully processed hello request for: {}", name);
        return response;
    }

    /**
     * Create address object with default values.
     * 
     * @param city The city name
     * @return Address object with populated fields
     */
    private Address createAddress(String city) {
        Address address = new Address();
        address.setStreet("123 Main Street");
        address.setCity(city);
        address.setState("Maharashtra");
        address.setCountry("India");
        return address;
    }
}
