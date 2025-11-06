# Quick Start Guide

## Prerequisites

- **Java 21 JDK** installed
- **Maven 3.9+** installed
- **curl** (for testing)
- **Docker** (optional, for containerized deployment)

## 5-Minute Quick Start

### 1. Build the Application

```bash
cd migrated_code
mvn clean package
```

Expected output: `BUILD SUCCESS`

### 2. Run the Application

```bash
mvn spring-boot:run
```

Wait for:
```
Started HelloMicroserviceApplication in X seconds
```

### 3. Test SOAP Endpoint

#### View WSDL

```bash
curl http://localhost:8080/ws/hello.wsdl
```

#### Send SOAP Request

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>John Doe</hel:name>
         <hel:city>Mumbai</hel:city>
         <hel:datetime>2025-11-06T10:00:00</hel:datetime>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

Expected Response:
```xml
<SOAP-ENV:Envelope ...>
   <SOAP-ENV:Body>
      <ns2:sayHelloResponse>
         <ns2:greeting>Hello John Doe from Mumbai! Current date/time: 2025-11-06T10:00:00</ns2:greeting>
         <ns2:address>
            <ns2:street>123 Main Street</ns2:street>
            <ns2:city>Mumbai</ns2:city>
            <ns2:state>Maharashtra</ns2:state>
            <ns2:country>India</ns2:country>
         </ns2:address>
      </ns2:sayHelloResponse>
   </SOAP-ENV:Body>
</SOAP-ENV:Envelope>
```

### 4. Test REST Endpoint

#### GET Request

```bash
curl "http://localhost:8080/api/hello?name=Jane&city=Delhi&datetime=2025-11-06"
```

Expected Response:
```json
{
  "greeting": "Hello Jane from Delhi! Current date/time: 2025-11-06",
  "address": {
    "street": "123 Main Street",
    "city": "Delhi",
    "state": "Maharashtra",
    "country": "India"
  }
}
```

#### POST Request

```bash
curl -X POST http://localhost:8080/api/hello \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Alice",
    "city": "Bangalore",
    "datetime": "2025-11-06T15:30:00"
  }'
```

### 5. Check Health

```bash
curl http://localhost:8080/actuator/health
```

Expected Response:
```json
{
  "status": "UP",
  "groups": ["liveness", "readiness"]
}
```

### 6. View API Documentation

Open in browser:
```
http://localhost:8080/swagger-ui.html
```

## Docker Quick Start

### Build Docker Image

```bash
cd migrated_code
docker build -t hello-microservice:latest .
```

### Run Container

```bash
docker run -p 8080:8080 hello-microservice:latest
```

### Using Docker Compose

```bash
docker-compose up
```

To stop:
```bash
docker-compose down
```

## Testing

### Run All Tests

```bash
mvn test
```

### Run Specific Test Class

```bash
mvn test -Dtest=HelloServiceTest
```

### Run with Coverage

```bash
mvn clean verify
```

## Accessing Key Endpoints

| Description | URL | Method |
|-------------|-----|--------|
| **SOAP WSDL** | http://localhost:8080/ws/hello.wsdl | GET |
| **SOAP Endpoint** | http://localhost:8080/ws | POST |
| **REST GET** | http://localhost:8080/api/hello?name=X&city=Y | GET |
| **REST POST** | http://localhost:8080/api/hello | POST |
| **Health Check** | http://localhost:8080/actuator/health | GET |
| **Liveness** | http://localhost:8080/actuator/health/liveness | GET |
| **Readiness** | http://localhost:8080/actuator/health/readiness | GET |
| **Metrics** | http://localhost:8080/actuator/metrics | GET |
| **Prometheus** | http://localhost:8080/actuator/prometheus | GET |
| **Swagger UI** | http://localhost:8080/swagger-ui.html | GET |
| **OpenAPI JSON** | http://localhost:8080/v3/api-docs | GET |

## Common Commands

### Development

```bash
# Clean build
mvn clean install

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# Skip tests
mvn clean package -DskipTests

# Debug mode
mvn spring-boot:run -Dspring-boot.run.jvmArguments="-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005"
```

### Docker

```bash
# Build
docker build -t hello-microservice:latest .

# Run detached
docker run -d -p 8080:8080 --name hello-service hello-microservice:latest

# View logs
docker logs -f hello-service

# Stop
docker stop hello-service

# Remove
docker rm hello-service

# Clean up
docker system prune -a
```

### Troubleshooting

```bash
# Check Java version
java -version

# Check Maven version
mvn -version

# Verify dependencies
mvn dependency:tree

# Check for updates
mvn versions:display-dependency-updates

# Generate sources
mvn clean generate-sources

# Debug build
mvn clean install -X
```

## Environment Variables

You can override configuration using environment variables:

```bash
# Change port
SERVER_PORT=9090 mvn spring-boot:run

# Set profile
SPRING_PROFILES_ACTIVE=prod mvn spring-boot:run

# With Docker
docker run -p 9090:9090 -e SERVER_PORT=9090 hello-microservice:latest
```

## Sample Requests

### SOAP (using SoapUI or Postman)

**Endpoint:** `http://localhost:8080/ws`

**Headers:**
```
Content-Type: text/xml
SOAPAction: ""
```

**Body:**
```xml
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
                  xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>Test User</hel:name>
         <hel:city>Chennai</hel:city>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>
```

### REST (using curl)

```bash
# Simple GET
curl "http://localhost:8080/api/hello?name=Test&city=Mumbai"

# With all parameters
curl "http://localhost:8080/api/hello?name=John&city=Delhi&datetime=2025-11-06T12:00:00"

# POST with JSON
curl -X POST http://localhost:8080/api/hello \
  -H "Content-Type: application/json" \
  -d '{"name":"Alice","city":"Pune","datetime":"2025-11-06"}'

# Pretty print JSON
curl -s "http://localhost:8080/api/hello?name=Bob&city=Kolkata" | jq .
```

## Performance Testing

### Using Apache Bench

```bash
# Install ab (Apache Bench)
# macOS: already installed
# Linux: sudo apt-get install apache2-utils

# Test REST endpoint
ab -n 1000 -c 10 "http://localhost:8080/api/hello?name=Test&city=Mumbai"
```

### Using wrk

```bash
# Install wrk
# macOS: brew install wrk

# Test
wrk -t4 -c100 -d30s "http://localhost:8080/api/hello?name=Test&city=Mumbai"
```

## Monitoring

### Health Check Loop

```bash
# Continuous health check
watch -n 2 curl -s http://localhost:8080/actuator/health | jq .
```

### Metrics

```bash
# View specific metric
curl http://localhost:8080/actuator/metrics/jvm.memory.used

# All metrics
curl http://localhost:8080/actuator/metrics | jq .
```

## IDE Setup

### IntelliJ IDEA

1. Import as Maven project
2. Set Project SDK to Java 21
3. Enable annotation processing
4. Run configuration:
   - Main class: `com.example.hello_microservice.HelloMicroserviceApplication`
   - VM options: `-Dspring.profiles.active=dev`

### VS Code

1. Install extensions:
   - Java Extension Pack
   - Spring Boot Extension Pack
2. Open folder: `migrated_code`
3. F5 to run/debug

### Eclipse

1. Import → Existing Maven Projects
2. Select `migrated_code` folder
3. Right-click → Run As → Spring Boot App

## Next Steps

- ✅ Review [README.md](README.md) for architecture details
- ✅ Check [MIGRATION_GUIDE.md](MIGRATION_GUIDE.md) for migration details
- ✅ Explore [TESTING.md](TESTING.md) for testing strategies
- ✅ Configure monitoring and alerting
- ✅ Set up CI/CD pipeline

## Support

For issues or questions:
1. Check logs: `tail -f logs/application.log`
2. Verify configuration: `application.yml`
3. Review documentation
4. Check Spring Boot actuator endpoints

## Shutdown

```bash
# Graceful shutdown (Ctrl+C in terminal)
# Or
curl -X POST http://localhost:8080/actuator/shutdown

# Docker
docker stop hello-service
```
