# Migration Guide: Java 15 SOAP to Java 21 Microservice

## Overview

This document provides a comprehensive guide for the migration from a Java 15 Spring-WS SOAP service to a Java 21 Spring Boot 3.x cloud-native microservice.

## Migration Summary

| Aspect | Before (Java 15) | After (Java 21) |
|--------|------------------|-----------------|
| **Java Version** | 15 | 21 (LTS) |
| **Spring Boot** | 2.x | 3.3.5 |
| **Namespace** | javax.* | jakarta.* |
| **JAXB** | javax.xml.bind | jakarta.xml.bind 4.x |
| **Architecture** | Monolithic SOAP | Microservice (SOAP + REST) |
| **Testing** | JUnit 4 | JUnit 5 |
| **Containerization** | None | Docker (eclipse-temurin:21) |
| **Observability** | Basic | Actuator, Prometheus, Health checks |

## Step-by-Step Migration Process

### Phase 1: Environment Setup

#### 1.1 Update Java Version

```bash
# Verify Java 21 installation
java -version
# Expected: openjdk version "21"
```

#### 1.2 Update Maven Version

```bash
# Ensure Maven 3.9+
mvn -version
```

### Phase 2: POM Transformation

#### 2.1 Update Parent POM

**Before:**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>2.7.x</version>
</parent>
```

**After:**
```xml
<parent>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-parent</artifactId>
    <version>3.3.5</version>
</parent>
```

#### 2.2 Update Java Version Properties

**Before:**
```xml
<properties>
    <java.version>15</java.version>
</properties>
```

**After:**
```xml
<properties>
    <java.version>21</java.version>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>
```

#### 2.3 Replace javax.* Dependencies

**Before:**
```xml
<dependency>
    <groupId>javax.xml.bind</groupId>
    <artifactId>jaxb-api</artifactId>
</dependency>
```

**After:**
```xml
<dependency>
    <groupId>jakarta.xml.bind</groupId>
    <artifactId>jakarta.xml.bind-api</artifactId>
    <version>4.0.2</version>
</dependency>
<dependency>
    <groupId>org.glassfish.jaxb</groupId>
    <artifactId>jaxb-runtime</artifactId>
    <version>4.0.5</version>
</dependency>
```

#### 2.4 Add New Dependencies

```xml
<!-- REST Support -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>

<!-- Observability -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>

<!-- OpenAPI Documentation -->
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.3.0</version>
</dependency>
```

#### 2.5 Update JAXB Maven Plugin

**Before:**
```xml
<plugin>
    <groupId>org.jvnet.jaxb2.maven2</groupId>
    <artifactId>maven-jaxb2-plugin</artifactId>
</plugin>
```

**After:**
```xml
<plugin>
    <groupId>org.jvnet.jaxb</groupId>
    <artifactId>jaxb-maven-plugin</artifactId>
    <version>4.0.8</version>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
    <configuration>
        <schemaDirectory>${project.basedir}/src/main/resources/wsdl</schemaDirectory>
        <schemaIncludes>
            <include>**/*.xsd</include>
        </schemaIncludes>
        <generateDirectory>${project.basedir}/src/main/java</generateDirectory>
    </configuration>
</plugin>
```

### Phase 3: Code Migration

#### 3.1 Namespace Changes

Apply these replacements across all Java files:

| Before | After |
|--------|-------|
| `import javax.xml.bind.*` | `import jakarta.xml.bind.*` |
| `import javax.xml.ws.*` | `import jakarta.xml.ws.*` |
| `import javax.servlet.*` | `import jakarta.servlet.*` |
| `import javax.annotation.*` | `import jakarta.annotation.*` |

#### 3.2 Update Endpoint Class

**Before:**
```java
package com.example.hello_ws.endpoint;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
// Business logic embedded

@Endpoint
public class HelloWorldEndpoint {
    @PayloadRoot(...)
    @ResponsePayload
    public SayHelloResponse sayHello(@RequestPayload SayHelloRequest request) {
        // Business logic here
        SayHelloResponse response = new SayHelloResponse();
        // ... implementation
        return response;
    }
}
```

**After:**
```java
package com.example.hello_microservice.endpoint;

import com.example.hello_microservice.service.HelloService;
// Constructor injection

@Endpoint
public class HelloWorldEndpoint {
    private final HelloService helloService;
    
    public HelloWorldEndpoint(HelloService helloService) {
        this.helloService = helloService;
    }
    
    @PayloadRoot(...)
    @ResponsePayload
    public SayHelloResponse sayHello(@RequestPayload SayHelloRequest request) {
        return helloService.processHelloRequest(request);
    }
}
```

#### 3.3 Extract Business Logic to Service

Create `HelloService.java`:

```java
package com.example.hello_microservice.service;

@Service
public class HelloService {
    public SayHelloResponse processHelloRequest(SayHelloRequest request) {
        // Business logic here
        SayHelloResponse response = new SayHelloResponse();
        // ... implementation
        return response;
    }
}
```

#### 3.4 Add REST Controller

Create `HelloRestController.java`:

```java
package com.example.hello_microservice.controller;

@RestController
@RequestMapping("/api")
public class HelloRestController {
    private final HelloService helloService;
    
    public HelloRestController(HelloService helloService) {
        this.helloService = helloService;
    }
    
    @GetMapping("/hello")
    public ResponseEntity<SayHelloResponse> sayHello(
            @RequestParam String name,
            @RequestParam String city,
            @RequestParam(required = false) String datetime) {
        
        SayHelloRequest request = new SayHelloRequest();
        request.setName(name);
        request.setCity(city);
        request.setDatetime(datetime);
        
        return ResponseEntity.ok(helloService.processHelloRequest(request));
    }
}
```

#### 3.5 Update Configuration

**Before:**
```java
package com.example.hello_ws.config;

@Configuration
public class WebServiceConfig {
    // Old configuration
}
```

**After:**
```java
package com.example.hello_microservice.config;

@Configuration
public class WebServiceConfig {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> 
            soapMessageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true); // Important!
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }
    
    @Bean(name = "hello")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema schema) {
        DefaultWsdl11Definition def = new DefaultWsdl11Definition();
        def.setPortTypeName("HelloWorldPort");
        def.setLocationUri("/ws");
        def.setTargetNamespace("http://example.com/hello");
        def.setSchema(schema);
        return def;
    }
}
```

### Phase 4: Testing Migration

#### 4.1 Update Test Dependencies

```xml
<!-- JUnit 5 (already in spring-boot-starter-test) -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-test</artifactId>
    <scope>test</scope>
</dependency>

<!-- Spring WS Test -->
<dependency>
    <groupId>org.springframework.ws</groupId>
    <artifactId>spring-ws-test</artifactId>
    <scope>test</scope>
</dependency>
```

#### 4.2 Migrate Test Classes

**Before (JUnit 4):**
```java
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpringRunner.class)
public class HelloServiceTest {
    @Test
    public void testHello() {
        // test
    }
}
```

**After (JUnit 5):**
```java
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

@SpringBootTest
@DisplayName("HelloService Unit Tests")
class HelloServiceTest {
    @Test
    @DisplayName("Should process hello request")
    void testHello() {
        // test with AssertJ
        assertThat(result).isNotNull();
    }
}
```

### Phase 5: Configuration Files

#### 5.1 Create application.yml

```yaml
spring:
  application:
    name: hello-microservice

server:
  port: 8080
  shutdown: graceful

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics,prometheus
  endpoint:
    health:
      show-details: always
      probes:
        enabled: true
  health:
    livenessState:
      enabled: true
    readinessState:
      enabled: true
```

### Phase 6: Containerization

#### 6.1 Create Dockerfile

```dockerfile
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN apt-get update && apt-get install -y maven && \
    mvn clean package -DskipTests

FROM eclipse-temurin:21-jre
WORKDIR /app
RUN groupadd -r appuser && useradd -r -g appuser appuser
COPY --from=build /app/target/*.jar app.jar
RUN chown appuser:appuser app.jar
USER appuser
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Phase 7: Validation

#### 7.1 Build Validation

```bash
mvn clean verify
```

Expected: BUILD SUCCESS

#### 7.2 Test Validation

```bash
mvn test
```

Expected: All tests pass

#### 7.3 Runtime Validation

```bash
mvn spring-boot:run
```

Check:
- ✅ Application starts without errors
- ✅ SOAP endpoint: `http://localhost:8080/ws/hello.wsdl`
- ✅ REST endpoint: `http://localhost:8080/api/hello?name=Test&city=Mumbai`
- ✅ Health: `http://localhost:8080/actuator/health`

#### 7.4 Docker Validation

```bash
docker build -t hello-microservice:latest .
docker run -p 8080:8080 hello-microservice:latest
```

## Common Issues & Solutions

### Issue 1: javax.* Classes Not Found

**Error:**
```
package javax.xml.bind does not exist
```

**Solution:**
- Replace all `javax.*` imports with `jakarta.*`
- Update dependencies to Jakarta versions

### Issue 2: JAXB Generation Fails

**Error:**
```
Failed to execute goal org.jvnet.jaxb:jaxb-maven-plugin
```

**Solution:**
- Ensure using `jaxb-maven-plugin` 4.x
- Check XSD files are in correct location
- Verify `generateDirectory` path

### Issue 3: Tests Fail After Migration

**Solution:**
- Update to JUnit 5 annotations (`@Test` → `org.junit.jupiter.api.Test`)
- Replace `@RunWith` with `@SpringBootTest`
- Update assertions to AssertJ style

### Issue 4: WSDL Not Accessible

**Solution:**
- Ensure `transformWsdlLocations=true` in config
- Check XSD file is in `classpath:wsdl/`
- Verify bean name matches WSDL endpoint

## Rollback Strategy

1. **Git Branch**: All changes in feature branch
2. **Backup**: Original code preserved in `backup/` folder
3. **Incremental**: Small commits per phase
4. **Testing**: Each phase validated before next

## Best Practices Applied

1. ✅ **Constructor Injection** over field injection
2. ✅ **Separation of Concerns** (Controller → Service → Repository)
3. ✅ **Immutable Configurations** where possible
4. ✅ **Comprehensive Logging** with SLF4J
5. ✅ **Cloud-Native** health checks and metrics
6. ✅ **Security** non-root Docker user
7. ✅ **Documentation** inline and external
8. ✅ **Testing** unit and integration coverage

## Performance Considerations

- Java 21 JVM improvements (virtual threads, pattern matching)
- Container-aware JVM settings
- Graceful shutdown for zero-downtime deployments
- Health check optimizations

## Next Steps

1. ☐ Set up CI/CD pipeline
2. ☐ Configure monitoring (Prometheus + Grafana)
3. ☐ Add distributed tracing (Zipkin/Jaeger)
4. ☐ Implement security (OAuth2/JWT)
5. ☐ Add rate limiting
6. ☐ Configure service mesh (if applicable)

## References

- [Spring Boot 3.x Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta EE 10 Specification](https://jakarta.ee/specifications/platform/10/)
- [Java 21 Features](https://openjdk.org/projects/jdk/21/)
