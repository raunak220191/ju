package com.example.hello_microservice.endpoint;

import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import com.example.hello_microservice.service.HelloService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

/**
 * SOAP Endpoint for HelloWorld service.
 * Migrated to Java 21 with jakarta.* imports and delegated business logic to HelloService.
 * Uses constructor injection (best practice).
 */
@Endpoint
public class HelloWorldEndpoint {

    private static final Logger logger = LoggerFactory.getLogger(HelloWorldEndpoint.class);
    private static final String NAMESPACE_URI = "http://example.com/hello";

    private final HelloService helloService;

    /**
     * Constructor injection for service dependency.
     */
    public HelloWorldEndpoint(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * SOAP operation handler for sayHello request.
     * Delegates to HelloService for business logic processing.
     */
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "sayHelloRequest")
    @ResponsePayload
    public SayHelloResponse sayHello(@RequestPayload SayHelloRequest request) {
        logger.info("SOAP request received for: {}", request.getName());
        return helloService.processHelloRequest(request);
    }
}
