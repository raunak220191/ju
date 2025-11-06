package com.example.hello_microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main Spring Boot Application class for Hello Microservice.
 * This is a cloud-native, Java 21-based microservice exposing both SOAP and REST endpoints.
 */
@SpringBootApplication
public class HelloMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloMicroserviceApplication.class, args);
    }
}
