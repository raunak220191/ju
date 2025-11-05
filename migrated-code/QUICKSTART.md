# 🚀 QUICK START GUIDE

## Prerequisites Check

Before starting, verify you have:

```bash
# Check Java version (must be 21)
java -version

# Check Maven version (must be 3.8+)
mvn -version
```

## Option 1: Quick Start (Automated)

### On Mac/Linux:
```bash
cd migrated-code
./build-and-run.sh
```

### On Windows:
```cmd
cd migrated-code
build-and-run.bat
```

## Option 2: Step-by-Step

### 1. Navigate to Project
```bash
cd /Users/raunakpandey/ju/migrated-code
```

### 2. Generate JAXB Classes
```bash
mvn clean jaxb40:generate
```

This creates:
- `src/main/java/com/example/hello/Address.java`
- `src/main/java/com/example/hello/SayHelloRequest.java`
- `src/main/java/com/example/hello/SayHelloResponse.java`
- `src/main/java/com/example/hello/ObjectFactory.java`
- `src/main/java/com/example/hello/package-info.java`

### 3. Compile
```bash
mvn compile
```

### 4. Run Tests
```bash
mvn test
```

### 5. Start the Service
```bash
mvn spring-boot:run
```

## 🧪 Verify Installation

Open a new terminal and test:

### Check Health Endpoint
```bash
curl http://localhost:8080/actuator/health
```

Expected response:
```json
{"status":"UP"}
```

### Check WSDL
```bash
curl http://localhost:8080/ws/hello.wsdl
```

### Send SOAP Request
```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -H "SOAPAction: sayHelloAction" \
  -d '<?xml version="1.0"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>Test User</hel:name>
         <hel:city>Mumbai</hel:city>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## 📚 Next Steps

1. **Read Documentation**: Check `README.md` for detailed information
2. **Migration Guide**: See `MIGRATION_GUIDE.md` for AI prompts and process
3. **Testing Examples**: Review `TESTING.md` for more test cases
4. **Customize**: Edit `application.properties` for your needs

## 🔧 Common Commands

| Command | Purpose |
|---------|---------|
| `mvn clean` | Clean build artifacts |
| `mvn jaxb40:generate` | Generate JAXB classes |
| `mvn compile` | Compile the project |
| `mvn test` | Run unit tests |
| `mvn package` | Create JAR file |
| `mvn spring-boot:run` | Start the application |

## 🐛 Troubleshooting

### JAXB Generation Fails
```bash
# Check if XSD file exists
ls -la src/main/resources/wsdl/hello.xsd

# Try with verbose output
mvn clean jaxb40:generate -X
```

### Port Already in Use
Edit `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Java Version Issues
```bash
# Verify JAVA_HOME
echo $JAVA_HOME

# Set to Java 21 if needed
export JAVA_HOME=/path/to/java21
```

## 📞 Support

For issues or questions, refer to:
- `README.md` - Full documentation
- `MIGRATION_GUIDE.md` - Detailed migration steps
- Spring Boot docs: https://spring.io/projects/spring-boot

---

**Enjoy your Java 21 Microservice! 🎉**
