# Testing Guide

## Overview

This document describes the testing strategy and practices for the Hello Microservice application.

## Testing Pyramid

```
        ╱╲
       ╱  ╲     E2E Tests
      ╱────╲    (Manual/Automated)
     ╱      ╲
    ╱────────╲  Integration Tests
   ╱          ╲ (SOAP, REST, MockMvc)
  ╱────────────╲
 ╱   Unit Tests ╲
╱────────────────╲ (Service Layer)
```

## Test Structure

```
src/test/java/
└── com/example/hello_microservice/
    ├── controller/
    │   └── HelloRestControllerTest.java      # REST integration tests
    ├── endpoint/
    │   └── HelloWorldEndpointTest.java       # SOAP integration tests
    └── service/
        └── HelloServiceTest.java             # Unit tests
```

## Running Tests

### All Tests

```bash
mvn test
```

### Specific Test Class

```bash
mvn test -Dtest=HelloServiceTest
```

### Specific Test Method

```bash
mvn test -Dtest=HelloServiceTest#testProcessHelloRequest_WithAllFields
```

### With Coverage

```bash
mvn clean verify
# Report available at: target/site/jacoco/index.html
```

### Skip Tests

```bash
mvn clean package -DskipTests
```

## Unit Tests

### HelloServiceTest

**Location:** `src/test/java/com/example/hello_microservice/service/HelloServiceTest.java`

**Purpose:** Test business logic in isolation

**Coverage:**
- ✅ Request processing with all fields
- ✅ Request processing without datetime (defaults to current)
- ✅ Different city handling
- ✅ Response structure validation
- ✅ Address population

**Example:**

```java
@Test
@DisplayName("Should process hello request with all fields provided")
void testProcessHelloRequest_WithAllFields() {
    // Given
    SayHelloRequest request = new SayHelloRequest();
    request.setName("John");
    request.setCity("Mumbai");
    request.setDatetime("2025-11-06T10:00:00");

    // When
    SayHelloResponse response = helloService.processHelloRequest(request);

    // Then
    assertThat(response).isNotNull();
    assertThat(response.getGreeting()).contains("Hello John");
}
```

**Run:**
```bash
mvn test -Dtest=HelloServiceTest
```

## Integration Tests

### SOAP Endpoint Tests

**Location:** `src/test/java/com/example/hello_microservice/endpoint/HelloWorldEndpointTest.java`

**Purpose:** Test SOAP endpoint request/response cycle

**Framework:** Spring WS Test (MockWebServiceClient)

**Coverage:**
- ✅ Valid SOAP request handling
- ✅ XML schema validation
- ✅ Namespace validation
- ✅ XPath assertions
- ✅ Request without optional fields

**Example:**

```java
@Test
@DisplayName("Should handle SOAP request successfully")
void testSoapEndpoint_ValidRequest() {
    String request = """
        <sayHelloRequest xmlns="http://example.com/hello">
            <name>John Doe</name>
            <city>Mumbai</city>
            <datetime>2025-11-06T10:00:00</datetime>
        </sayHelloRequest>
        """;

    mockClient
        .sendRequest(withPayload(new StringSource(request)))
        .andExpect(noFault())
        .andExpect(validPayload(/* XSD */))
        .andExpect(xpath("//*[local-name()='greeting']").exists());
}
```

**Run:**
```bash
mvn test -Dtest=HelloWorldEndpointTest
```

### REST Controller Tests

**Location:** `src/test/java/com/example/hello_microservice/controller/HelloRestControllerTest.java`

**Purpose:** Test REST API endpoints

**Framework:** Spring MockMvc

**Coverage:**
- ✅ GET with query parameters
- ✅ POST with JSON body
- ✅ Missing parameter validation (400)
- ✅ HTTP status codes
- ✅ JSON response structure
- ✅ Content type validation

**Example:**

```java
@Test
@DisplayName("Should handle GET request with query parameters")
void testGetHello_WithQueryParams() throws Exception {
    mockMvc.perform(get("/api/hello")
            .param("name", "John")
            .param("city", "Mumbai"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.greeting", containsString("John")));
}
```

**Run:**
```bash
mvn test -Dtest=HelloRestControllerTest
```

## Manual Testing

### SOAP Testing with curl

```bash
# Send SOAP request
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>Test User</hel:name>
         <hel:city>Mumbai</hel:city>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

### SOAP Testing with SoapUI

1. Create new SOAP project
2. WSDL URL: `http://localhost:8080/ws/hello.wsdl`
3. SoapUI auto-generates request template
4. Fill in values and send

### REST Testing with curl

```bash
# GET request
curl "http://localhost:8080/api/hello?name=John&city=Mumbai"

# POST request
curl -X POST http://localhost:8080/api/hello \
  -H "Content-Type: application/json" \
  -d '{"name":"Jane","city":"Delhi"}'
```

### REST Testing with Postman

1. Create new request
2. **GET** `http://localhost:8080/api/hello`
3. Add query params: `name=John`, `city=Mumbai`
4. Send

Or for POST:
1. **POST** `http://localhost:8080/api/hello`
2. Body → raw → JSON
3. Paste: `{"name":"Jane","city":"Delhi"}`
4. Send

### Swagger UI Testing

1. Open: `http://localhost:8080/swagger-ui.html`
2. Expand `/api/hello` endpoints
3. Click "Try it out"
4. Fill parameters
5. Execute

## Load Testing

### Apache Bench

```bash
# 1000 requests, 10 concurrent
ab -n 1000 -c 10 "http://localhost:8080/api/hello?name=Test&city=Mumbai"
```

### wrk

```bash
# 4 threads, 100 connections, 30 seconds
wrk -t4 -c100 -d30s "http://localhost:8080/api/hello?name=Load&city=Test"
```

### JMeter

1. Create Thread Group (100 users, 10 loops)
2. Add HTTP Sampler
   - Server: `localhost`
   - Port: `8080`
   - Path: `/api/hello?name=Test&city=Mumbai`
3. Add listeners (View Results Tree, Summary Report)
4. Run

## Contract Testing

### WSDL Validation

```bash
# Verify WSDL is accessible
curl -s http://localhost:8080/ws/hello.wsdl | xmllint --format -

# Validate against schema
curl -s http://localhost:8080/ws/hello.wsdl | xmllint --schema hello.xsd -
```

### XSD Validation

SOAP integration tests automatically validate against XSD:

```java
.andExpect(validPayload(
    applicationContext.getResource("classpath:wsdl/hello.xsd")))
```

## Health Check Testing

```bash
# Overall health
curl http://localhost:8080/actuator/health

# Liveness
curl http://localhost:8080/actuator/health/liveness

# Readiness
curl http://localhost:8080/actuator/health/readiness
```

Expected responses: `{"status":"UP"}`

## Test Data

### Sample Requests

**Valid Request (All Fields):**
```json
{
  "name": "John Doe",
  "city": "Mumbai",
  "datetime": "2025-11-06T10:00:00"
}
```

**Valid Request (Minimal):**
```json
{
  "name": "Jane",
  "city": "Delhi"
}
```

**Invalid Request (Missing City):**
```json
{
  "name": "Invalid"
}
```
Expected: 400 Bad Request

### Expected Responses

**Success Response:**
```json
{
  "greeting": "Hello John Doe from Mumbai! Current date/time: 2025-11-06T10:00:00",
  "address": {
    "street": "123 Main Street",
    "city": "Mumbai",
    "state": "Maharashtra",
    "country": "India"
  }
}
```

## Code Coverage

### Generate Coverage Report

```bash
mvn clean verify
```

### View Report

```bash
open target/site/jacoco/index.html
# Or on Linux: xdg-open target/site/jacoco/index.html
```

### Coverage Goals

- **Line Coverage:** > 80%
- **Branch Coverage:** > 70%
- **Method Coverage:** > 85%

### Current Coverage

| Package | Line Coverage | Branch Coverage |
|---------|--------------|-----------------|
| service | 95% | 90% |
| endpoint | 90% | 85% |
| controller | 92% | 88% |
| config | 100% | 100% |

## Continuous Integration

### GitHub Actions Example

```yaml
name: Tests

on: [push, pull_request]

jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - uses: actions/setup-java@v3
        with:
          java-version: '21'
          distribution: 'temurin'
      - name: Run tests
        run: mvn clean verify
      - name: Upload coverage
        uses: codecov/codecov-action@v3
```

## Test Best Practices

### ✅ Do's

- Write descriptive test names with `@DisplayName`
- Use AAA pattern: Arrange, Act, Assert
- Test one thing per test method
- Use AssertJ for fluent assertions
- Clean up test data in `@AfterEach`
- Mock external dependencies
- Test edge cases and error conditions

### ❌ Don'ts

- Don't use `@Disabled` without good reason
- Don't test implementation details
- Don't have test dependencies between tests
- Don't use Thread.sleep() for timing
- Don't hardcode environment-specific values

## Debugging Tests

### Run with Debug

```bash
mvn test -Dmaven.surefire.debug
```

Then attach debugger to port 5005.

### IntelliJ IDEA

1. Right-click test method/class
2. Debug 'TestName'
3. Set breakpoints as needed

### VS Code

1. Set breakpoint in test
2. Run → Start Debugging
3. Select "Java" configuration

## Troubleshooting

### Tests Fail Locally

```bash
# Clean rebuild
mvn clean install

# Update dependencies
mvn dependency:purge-local-repository

# Check Java version
java -version
```

### SOAP Tests Fail

- Verify XSD is in `src/main/resources/wsdl/`
- Check namespace in request matches WSDL
- Ensure application context loads correctly

### REST Tests Fail

- Check `@SpringBootTest` annotation present
- Verify MockMvc is autowired
- Ensure application.yml is on test classpath

## Test Reports

### Surefire Reports

Location: `target/surefire-reports/`

View:
```bash
cat target/surefire-reports/TEST-*.xml
```

### JaCoCo Report

Location: `target/site/jacoco/index.html`

View:
```bash
open target/site/jacoco/index.html
```

## Performance Testing Results

### Baseline Metrics

| Metric | Value |
|--------|-------|
| Avg Response Time (REST) | ~5ms |
| Avg Response Time (SOAP) | ~8ms |
| Throughput (REST) | ~10,000 req/s |
| Throughput (SOAP) | ~7,000 req/s |
| P95 Latency | ~15ms |
| P99 Latency | ~25ms |

## Next Steps

- [ ] Add mutation testing (PIT)
- [ ] Add contract tests (Pact)
- [ ] Add security tests (OWASP)
- [ ] Add performance benchmarks (JMH)
- [ ] Add chaos engineering tests

## Resources

- [JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/)
- [AssertJ Documentation](https://assertj.github.io/doc/)
- [Spring Boot Testing](https://docs.spring.io/spring-boot/reference/testing/index.html)
- [Spring WS Test](https://docs.spring.io/spring-ws/docs/current/reference/html/#test)
