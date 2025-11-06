# Hello Microservice - Java 21 Spring Boot 3.x

## Overview

This is a cloud-native microservice application migrated from Java 15 to Java 21, built with Spring Boot 3.x. It provides both SOAP (Spring-WS) and REST endpoints exposing the same business logic.

## Features

- ✅ **Java 21** with Spring Boot 3.3.5
- ✅ **Dual API Support**: SOAP (via Spring-WS) and REST (via Spring MVC)
- ✅ **Cloud-Native**: Actuator, health checks, metrics, containerized
- ✅ **Jakarta EE**: Migrated from `javax.*` to `jakarta.*`
- ✅ **JAXB Code Generation**: XSD-driven type generation
- ✅ **Microservice Architecture**: Layered design with proper separation
- ✅ **Comprehensive Testing**: Unit and integration tests (JUnit 5)
- ✅ **Observability**: Actuator endpoints, Prometheus metrics, OpenAPI docs
- ✅ **Docker Support**: Multi-stage Dockerfile with health checks

## Architecture

```
┌─────────────────────────────────────────┐
│          Client Applications            │
└──────────┬──────────────────┬───────────┘
           │                  │
      SOAP │                  │ REST
      /ws  │                  │ /api/hello
           │                  │
┌──────────▼──────────────────▼───────────┐
│         Spring Boot 3.x App             │
│  ┌────────────┐      ┌────────────┐    │
│  │   SOAP     │      │    REST    │    │
│  │ Endpoint   │      │ Controller │    │
│  └─────┬──────┘      └──────┬─────┘    │
│        └──────────┬──────────┘          │
│              ┌────▼─────┐               │
│              │ Service  │               │
│              │  Layer   │               │
│              └────┬─────┘               │
│              ┌────▼─────┐               │
│              │   JAXB   │               │
│              │  Models  │               │
│              └──────────┘               │
└─────────────────────────────────────────┘
```

## Technology Stack

- **Java**: 21 (LTS)
- **Spring Boot**: 3.3.5
- **Spring Web Services**: For SOAP endpoints
- **Spring MVC**: For REST endpoints
- **Jakarta XML Binding (JAXB)**: 4.x
- **SpringDoc OpenAPI**: 2.3.0 (REST API documentation)
- **JUnit 5**: Testing framework
- **AssertJ**: Fluent assertions
- **Docker**: Containerization with eclipse-temurin:21

## Project Structure

```
migrated_code/
├── src/
│   ├── main/
│   │   ├── java/com/example/
│   │   │   ├── hello/                      # JAXB generated models
│   │   │   │   ├── SayHelloRequest.java
│   │   │   │   ├── SayHelloResponse.java
│   │   │   │   └── Address.java
│   │   │   └── hello_microservice/
│   │   │       ├── HelloMicroserviceApplication.java
│   │   │       ├── config/
│   │   │       │   └── WebServiceConfig.java
│   │   │       ├── controller/
│   │   │       │   └── HelloRestController.java
│   │   │       ├── endpoint/
│   │   │       │   └── HelloWorldEndpoint.java
│   │   │       └── service/
│   │   │           └── HelloService.java
│   │   └── resources/
│   │       ├── application.yml
│   │       └── wsdl/
│   │           ├── hello.wsdl
│   │           └── hello.xsd
│   └── test/
│       └── java/com/example/hello_microservice/
│           ├── controller/
│           │   └── HelloRestControllerTest.java
│           ├── endpoint/
│           │   └── HelloWorldEndpointTest.java
│           └── service/
│               └── HelloServiceTest.java
├── Dockerfile
├── docker-compose.yml
└── pom.xml
```

## Quick Start

### Prerequisites

- Java 21 (JDK)
- Maven 3.9+
- Docker (optional, for containerization)

### Build

```bash
mvn clean verify
```

### Run

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### Run with Docker

```bash
# Build image
docker build -t hello-microservice:latest .

# Run container
docker run -p 8080:8080 hello-microservice:latest

# Or use Docker Compose
docker-compose up
```

## API Endpoints

### SOAP Endpoint

- **WSDL**: `http://localhost:8080/ws/hello.wsdl`
- **Endpoint**: `http://localhost:8080/ws`

**Sample SOAP Request:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>John Doe</hel:name>
         <hel:city>Mumbai</hel:city>
         <hel:datetime>2025-11-06T10:00:00</hel:datetime>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### REST Endpoints

#### GET `/api/hello`

```bash
curl "http://localhost:8080/api/hello?name=John&city=Mumbai&datetime=2025-11-06T10:00:00"
```

**Response:**
```json
{
  "greeting": "Hello John from Mumbai! Current date/time: 2025-11-06T10:00:00",
  "address": {
    "street": "123 Main Street",
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "India"
  }
}
```

#### POST `/api/hello`

```bash
curl -X POST http://localhost:8080/api/hello \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Jane",
    "city": "Delhi",
    "datetime": "2025-11-06"
  }'
```

### Actuator Endpoints (Observability)

- **Health**: `http://localhost:8080/actuator/health`
- **Liveness**: `http://localhost:8080/actuator/health/liveness`
- **Readiness**: `http://localhost:8080/actuator/health/readiness`
- **Info**: `http://localhost:8080/actuator/info`
- **Metrics**: `http://localhost:8080/actuator/metrics`
- **Prometheus**: `http://localhost:8080/actuator/prometheus`

### OpenAPI Documentation

- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON**: `http://localhost:8080/v3/api-docs`

## Testing

### Run All Tests

```bash
mvn test
```

### Test Coverage

- **Unit Tests**: Service layer business logic
- **SOAP Integration Tests**: Full SOAP request/response cycle using spring-ws-test
- **REST Integration Tests**: HTTP endpoints using MockMvc

## Cloud-Native Features

### Health Checks

The application implements:
- **Liveness Probe**: `/actuator/health/liveness`
- **Readiness Probe**: `/actuator/health/readiness`

### Metrics & Observability

- Prometheus metrics export at `/actuator/prometheus`
- Detailed health information
- Application info endpoint

### Graceful Shutdown

Configured in `application.yml`:
```yaml
server:
  shutdown: graceful
```

### Container Optimizations

- Non-root user execution
- Health checks in Dockerfile
- JVM container awareness (`-XX:+UseContainerSupport`)
- Memory limits (`-XX:MaxRAMPercentage=75.0`)

## Migration Notes

### Key Changes from Java 15 → Java 21

1. **Namespace Migration**: All `javax.*` imports changed to `jakarta.*`
2. **Spring Boot**: Upgraded from 2.x to 3.3.5
3. **JAXB**: Updated to Jakarta XML Binding 4.x
4. **Java Version**: Compiler and runtime set to Java 21
5. **Architecture**: Extracted business logic into service layer
6. **REST Facade**: Added mandatory REST controller alongside SOAP endpoint
7. **Testing**: Migrated to JUnit 5 with modern assertions
8. **Observability**: Added comprehensive Actuator configuration

### Backward Compatibility

- WSDL/XSD contract remains unchanged
- SOAP endpoint maintains same namespace and operations
- Bean names kept stable (e.g., "hello" for WSDL definition)

## Development

### Adding New Operations

1. Update XSD schema in `src/main/resources/wsdl/hello.xsd`
2. Regenerate JAXB classes: `mvn clean generate-sources`
3. Add business logic in `HelloService`
4. Expose via SOAP in `HelloWorldEndpoint`
5. Expose via REST in `HelloRestController`
6. Write tests

### Configuration

Main configuration in `src/main/resources/application.yml`:
- Server port
- Actuator settings
- Logging levels
- Spring profiles

## Troubleshooting

### Build Issues

```bash
# Clean build
mvn clean install -U

# Skip tests
mvn clean package -DskipTests
```

### JAXB Generation Issues

```bash
# Force regeneration
mvn clean generate-sources
```

### Docker Issues

```bash
# Rebuild without cache
docker build --no-cache -t hello-microservice:latest .

# Check logs
docker logs hello-microservice
```

## License

Copyright © 2025. All rights reserved.
