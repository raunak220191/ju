package com.example.hello_microservice.controller;

import com.example.hello.SayHelloRequest;
import com.example.hello.SayHelloResponse;
import com.example.hello_microservice.service.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller providing REST API facade over the same business logic.
 * This is mandatory as per migration requirements: expose /api/hello endpoint.
 * Cloud-compliant with proper HTTP semantics and observability.
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Hello API", description = "REST API for Hello operations")
public class HelloRestController {

    private static final Logger logger = LoggerFactory.getLogger(HelloRestController.class);
    
    private final HelloService helloService;

    /**
     * Constructor injection for service dependency.
     */
    public HelloRestController(HelloService helloService) {
        this.helloService = helloService;
    }

    /**
     * REST endpoint for hello operation.
     * Maps to same service layer as SOAP endpoint.
     * 
     * @param name Name of the person
     * @param city City name
     * @param datetime Optional datetime string
     * @return Response with greeting and address
     */
    @GetMapping(value = "/hello", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Say hello", description = "Returns a greeting message with address details")
    public ResponseEntity<SayHelloResponse> sayHello(
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam(required = false) String datetime) {
        
        logger.info("REST request received for name: {}, city: {}", name, city);
        
        // Create request object
        SayHelloRequest request = new SayHelloRequest();
        request.setName(name);
        request.setCity(city);
        request.setDatetime(datetime);
        
        // Delegate to service
        SayHelloResponse response = helloService.processHelloRequest(request);
        
        return ResponseEntity.ok(response);
    }

    /**
     * POST endpoint for hello operation (accepts JSON body).
     */
    @PostMapping(value = "/hello", 
                 consumes = MediaType.APPLICATION_JSON_VALUE,
                 produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Say hello (POST)", description = "Returns a greeting message with address details")
    public ResponseEntity<SayHelloResponse> sayHelloPost(@RequestBody SayHelloRequest request) {
        logger.info("REST POST request received for: {}", request.getName());
        SayHelloResponse response = helloService.processHelloRequest(request);
        return ResponseEntity.ok(response);
    }
}
