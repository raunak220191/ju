package com.example.hello_ws.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import com.example.hello.Address;

@Endpoint
public class HelloWorldEndpoint {

    private static final String NAMESPACE_URI = "http://example.com/hello";

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sayHelloRequest")
    @ResponsePayload
    public SayHelloResponse sayHello(@RequestPayload SayHelloRequest request) {
        SayHelloResponse response = new SayHelloResponse();
        String name = request.getName();
        String city = request.getCity();
        String datetime = request.getDatetime();

        if (datetime == null || datetime.isBlank()) {
            datetime = java.time.LocalDateTime.now().toString();
        }

        // Greeting message
        response.setGreeting("Hello " + name + " from " + city + "! Current date/time: " + datetime);

        // Create Address object
        Address address = new Address();
        address.setStreet("123 Main Street");
        address.setCity(city);
        address.setState("Maharashtra");
        address.setCountry("India");

        // Attach address to response
        response.setAddress(address);

        return response;
    }
}