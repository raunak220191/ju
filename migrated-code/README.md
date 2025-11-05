# Hello World SOAP Microservice - Java 21

A modern Java 21 microservice implementing a SOAP web service using Spring Boot 3.5.7.

## 🚀 Features

- **Java 21** with modern language features
- **Spring Boot 3.5.7** for enterprise-grade microservice
- **JAXB 4.0** for XML binding (Jakarta EE)
- **Auto-generated POJOs** from XSD schema
- **Spring Actuator** for monitoring and health checks
- **Contract-First** SOAP service with WSDL
- **Comprehensive Unit Tests**

## 📋 Prerequisites

- Java 21 (JDK 21)
- Maven 3.8+
- IDE (IntelliJ IDEA, Eclipse, or VS Code)

## 🏗️ Project Structure

```
migrated-code/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/hello_ws/
│   │   │       ├── config/
│   │   │       │   └── WebServiceConfig.java
│   │   │       ├── endpoint/
│   │   │       │   └── HelloWorldEndpoint.java
│   │   │       └── HelloWsMicroserviceApplication.java
│   │   └── resources/
│   │       ├── wsdl/
│   │       │   ├── hello.xsd
│   │       │   └── hello.wsdl
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/example/hello_ws/
│               ├── endpoint/
│               │   └── HelloWorldEndpointTest.java
│               └── HelloWsMicroserviceApplicationTests.java
└── pom.xml
```

## 🛠️ Build & Run

### 1. Generate JAXB Classes from XSD

```bash
cd migrated-code
mvn clean jaxb40:generate
```

This will generate the following classes in `src/main/java/com/example/hello/`:
- `SayHelloRequest.java`
- `SayHelloResponse.java`
- `Address.java`
- `ObjectFactory.java`
- `package-info.java`

### 2. Compile the Project

```bash
mvn clean compile
```

### 3. Run Tests

```bash
mvn test
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or package and run as JAR:

```bash
mvn clean package
java -jar target/hello-ws-microservice-1.0.0.jar
```

## 📡 API Endpoints

### SOAP Service
- **Endpoint**: `http://localhost:8080/ws`
- **WSDL**: `http://localhost:8080/ws/hello.wsdl`

### Actuator Endpoints
- **Health**: `http://localhost:8080/actuator/health`
- **Info**: `http://localhost:8080/actuator/info`
- **Metrics**: `http://localhost:8080/actuator/metrics`

## 🧪 Testing with SOAP UI or cURL

### Sample SOAP Request

```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                  xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>John Doe</hel:name>
         <hel:city>Mumbai</hel:city>
         <hel:datetime>2025-11-05 10:30:00</hel:datetime>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### Using cURL

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml; charset=utf-8" \
  -H "SOAPAction: sayHelloAction" \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>John Doe</hel:name>
         <hel:city>Mumbai</hel:city>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## 📝 Migration Changes from Java 15 to Java 21

### Key Updates:
1. **Java Version**: Upgraded from Java 15 to Java 21 (LTS)
2. **Spring Boot**: Using Spring Boot 3.5.7 (requires Java 17+)
3. **Jakarta EE**: Migrated from `javax.*` to `jakarta.*` namespace
4. **JAXB**: Using Jakarta XML Binding API 4.0
5. **Modern Logging**: Added SLF4J logging with proper patterns
6. **Actuator**: Added Spring Boot Actuator for microservice monitoring
7. **Project Structure**: Proper Maven standard directory structure
8. **Unit Tests**: JUnit 5 (Jupiter) test cases

### Benefits of Java 21:
- Record classes (if needed for DTOs)
- Pattern matching for switch
- Virtual threads (Project Loom)
- Improved performance and security
- Long-term support (LTS release)

## 🔧 Configuration

Edit `src/main/resources/application.properties` to customize:
- Server port
- Logging levels
- Actuator endpoints
- Context path

## 📦 Dependencies

- Spring Boot Starter Web Services
- Spring Boot Actuator
- Jakarta XML Binding API 4.0
- JAXB Runtime (Glassfish)
- WSDL4J
- JUnit 5 (Testing)

## 🎯 Best Practices Implemented

1. **Contract-First Design**: XSD schema defines the contract
2. **Auto-Generation**: POJOs generated from schema
3. **Proper Logging**: SLF4J for structured logging
4. **Health Checks**: Actuator endpoints for monitoring
5. **Unit Testing**: Comprehensive test coverage
6. **Clean Code**: Well-documented and organized
7. **Maven Standards**: Standard directory structure

## 📄 License

MIT License

## 👨‍💻 Author

Generated as a Java 21 Microservice Migration
