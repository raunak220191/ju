# Migration Guide: Java 15 SOAP Service → Java 21 Microservice

## 📋 Overview

This guide provides the complete process and AI prompts to migrate a Java 15 WSDL SOAP service to a Java 21 microservice using Spring Boot 3.x.

---

## 🎯 Prompt Template for AI-Assisted Migration

### Prompt 1: Initial Assessment

```
I have a Java 15 SOAP web service that I need to migrate to Java 21 microservice. 
Here are my current files:

1. hello.xsd - XML Schema Definition
2. hello.wsdl - WSDL file
3. HelloWorldEndpoint.java - SOAP endpoint implementation
4. WebServiceConfig.java - Spring configuration
5. pom.xml - Maven configuration with jaxb40-maven-plugin

The JAXB plugin generates these classes:
- Address.java
- ObjectFactory.java
- package-info.java
- SayHelloRequest.java
- SayHelloResponse.java

Please help me:
1. Analyze the current setup
2. Identify what needs to be changed for Java 21
3. Suggest the migration strategy
4. Provide updated configuration files
```

### Prompt 2: Spring Boot 3.x Migration

```
Convert my SOAP service to use Spring Boot 3.x with Java 21. 
Please ensure:
1. Migration from javax.* to jakarta.* namespace
2. Use Jakarta XML Binding API 4.0
3. Update Maven dependencies for Spring Boot 3.5.7
4. Keep the same SOAP functionality
5. Add Spring Boot Actuator for microservice monitoring
6. Use modern Java 21 features where appropriate
```

### Prompt 3: Project Structure

```
Create a proper Maven project structure for my Java 21 microservice:
1. Standard src/main/java directory layout
2. Separate config, endpoint, and model packages
3. Resources folder with wsdl/ subdirectory
4. Test structure with unit tests
5. Generate a comprehensive README.md
```

### Prompt 4: Configuration Optimization

```
Optimize my Spring Boot configuration for production:
1. Add application.properties with logging configuration
2. Configure actuator endpoints
3. Set up proper error handling
4. Add health check endpoints
5. Include Docker support (optional)
```

### Prompt 5: Testing Strategy

```
Create a comprehensive testing strategy:
1. Unit tests for the SOAP endpoint
2. Integration tests for the web service
3. Mock tests for generated JAXB classes
4. Use JUnit 5 (Jupiter)
5. Include test coverage for edge cases
```

---

## 🔄 Migration Process (Step-by-Step)

### Step 1: Backup Original Code
```bash
cp -r original-project backup-$(date +%Y%m%d)
```

### Step 2: Create New Project Structure
```bash
mkdir -p migrated-code/src/main/{java,resources/wsdl}
mkdir -p migrated-code/src/test/java
```

### Step 3: Update pom.xml
- Change Java version to 21
- Update Spring Boot to 3.5.7
- Migrate to Jakarta dependencies
- Keep jaxb40-maven-plugin

### Step 4: Copy and Update Files
1. Copy XSD and WSDL to `src/main/resources/wsdl/`
2. Update Java files with Jakarta imports
3. Create main application class
4. Update configuration files

### Step 5: Generate JAXB Classes
```bash
mvn clean jaxb40:generate
```

### Step 6: Build and Test
```bash
mvn clean compile
mvn test
mvn spring-boot:run
```

---

## 🎨 AI Prompts for Specific Tasks

### For Code Review
```
Review my migrated code and check for:
1. Java 21 best practices
2. Spring Boot 3.x patterns
3. Security vulnerabilities
4. Performance optimizations
5. Missing error handling
```

### For Documentation
```
Generate comprehensive documentation for:
1. API endpoints and usage
2. SOAP request/response examples
3. Setup and deployment instructions
4. Troubleshooting guide
5. Configuration options
```

### For Docker Support
```
Create Docker support for my Java 21 microservice:
1. Multi-stage Dockerfile
2. docker-compose.yml for local testing
3. Kubernetes deployment files (optional)
4. Environment-specific configurations
```

### For Performance Tuning
```
Optimize my microservice for production:
1. Configure connection pooling
2. Add caching where appropriate
3. Implement rate limiting
4. Set up monitoring and metrics
5. Configure JVM options for Java 21
```

---

## 📊 Key Migration Changes

| Aspect | Java 15 (Old) | Java 21 (New) |
|--------|---------------|---------------|
| Java Version | 15 | 21 (LTS) |
| Spring Boot | 2.x | 3.5.7 |
| Namespace | javax.* | jakarta.* |
| JAXB API | 2.x | 4.0.2 |
| Min Java | 11+ | 17+ |
| JUnit | 4.x | 5 (Jupiter) |

---

## ✅ Validation Checklist

- [ ] Java 21 JDK installed
- [ ] Maven 3.8+ installed
- [ ] Dependencies updated to Jakarta
- [ ] JAXB classes generated successfully
- [ ] Application starts without errors
- [ ] WSDL accessible at /ws/hello.wsdl
- [ ] SOAP endpoint responds correctly
- [ ] Unit tests pass
- [ ] Actuator endpoints working
- [ ] Logs properly formatted

---

## 🚀 Advanced Features (Optional)

### Add REST API alongside SOAP
```
Create a REST API wrapper for my SOAP service:
1. Add Spring Boot Starter Web
2. Create REST controllers
3. Map SOAP operations to REST endpoints
4. Add OpenAPI/Swagger documentation
```

### Add Database Integration
```
Integrate database support:
1. Add Spring Data JPA
2. Create entity models
3. Implement repository layer
4. Add transaction management
```

### Add Security
```
Implement security for my microservice:
1. Add Spring Security
2. Configure authentication
3. Implement API key validation
4. Add HTTPS support
```

---

## 🔍 Troubleshooting Prompts

### Build Issues
```
I'm getting build errors when generating JAXB classes:
[paste error message]

Please help me:
1. Identify the root cause
2. Provide the fix
3. Explain the solution
```

### Runtime Issues
```
My application starts but SOAP endpoint returns error:
[paste error message and stack trace]

Context:
- Java version: 21
- Spring Boot: 3.5.7
- Request: [paste SOAP request]
```

### Configuration Issues
```
I need help configuring:
1. Custom SOAP headers
2. WS-Security
3. MTOM attachments
4. Custom exception handling

Please provide configuration examples.
```

---

## 📚 Resources

- [Spring Boot 3.x Migration Guide](https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-3.0-Migration-Guide)
- [Jakarta EE 10 Documentation](https://jakarta.ee/specifications/platform/10/)
- [Java 21 Features](https://openjdk.org/projects/jdk/21/)
- [JAXB Maven Plugin](https://github.com/phax/ph-jaxb-plugin)

---

## 🎓 Learning Path

1. **Understand the differences**: Java 15 → Java 21
2. **Study Jakarta EE**: javax → jakarta migration
3. **Learn Spring Boot 3.x**: New features and changes
4. **Practice with examples**: Build simple SOAP services
5. **Apply to your project**: Step-by-step migration

---

*This guide is designed to work with AI assistants like GitHub Copilot, ChatGPT, or Claude for automated migration assistance.*
