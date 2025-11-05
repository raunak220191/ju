# Java Migration Project

This repository contains a complete migration example of a Java 15 SOAP service to a Java 21 microservice using Spring Boot 3.x.

## 📁 Repository Structure

```
java_migration/
├── 📂 agentic migration/         # AI agent instructions and prompts
│   ├── ins_agent.yaml           # Agent configuration for migration
│   └── ins_prompt.md            # Ready-to-use prompt for Claude Sonnet 4.5
├── 📂 migrated-code/            # Complete migrated Java 21 microservice
│   ├── src/                     # Java 21 Spring Boot 3.x source code
│   ├── pom.xml                  # Updated Maven configuration
│   ├── README.md                # Detailed documentation
│   ├── QUICKSTART.md            # 5-minute setup guide
│   ├── MIGRATION_GUIDE.md       # Step-by-step migration process
│   ├── TESTING.md               # Testing examples and tools
│   └── build-and-run.sh/.bat    # Build and run scripts
├── hello.xsd                    # Original XML Schema Definition
├── hello.wsdl                   # Original WSDL file
├── HelloWorldEndpoint.java      # Original endpoint implementation
├── WebServiceConfig.java        # Original Spring configuration
├── pom.xml                      # Original Maven configuration
└── README.md                    # This file
```

## 🚀 Quick Start

### Option 1: Use the Migrated Code (Recommended)
```bash
cd migrated-code
./build-and-run.sh  # Mac/Linux
# or
build-and-run.bat   # Windows
```

### Option 2: Follow the Migration Process
1. Read `agentic migration/ins_prompt.md`
2. Copy the prompt to Claude Sonnet 4.5 Agentic Copilot in VS Code
3. Follow the step-by-step instructions

## 🎯 What's Inside

### Original Java 15 SOAP Service
- Traditional Spring-WS SOAP service
- JAXB code generation from XSD
- Basic SOAP endpoint implementation

### Migrated Java 21 Microservice  
- ✅ **Java 21** with modern language features
- ✅ **Spring Boot 3.5.7** for microservice architecture
- ✅ **Jakarta EE** namespace migration (javax.* → jakarta.*)
- ✅ **JAXB 4.0** for XML binding
- ✅ **Spring Actuator** for monitoring and health checks
- ✅ **Comprehensive tests** with JUnit 5
- ✅ **Complete documentation** and guides
- ✅ **Docker support** (Dockerfile included)

## 🤖 AI-Assisted Migration

This project includes specific prompts and instructions for **Claude Sonnet 4.5 Agentic Copilot in VS Code** to help you migrate similar SOAP services.

### Files for AI Migration:
- `agentic migration/ins_agent.yaml` - Agent configuration
- `agentic migration/ins_prompt.md` - Copy-paste prompt for Claude

## 📚 Documentation

| Document | Purpose |
|----------|---------|
| `migrated-code/README.md` | Complete project documentation |
| `migrated-code/QUICKSTART.md` | Get started in 5 minutes |
| `migrated-code/MIGRATION_GUIDE.md` | Detailed migration process |
| `migrated-code/TESTING.md` | Testing examples and tools |

## 🧪 Test the Service

After running the migrated service:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Get WSDL
curl http://localhost:8080/ws/hello.wsdl

# Send SOAP request
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml" \
  -d '<?xml version="1.0"?>...'  # See TESTING.md for complete examples
```

## 🎯 Key Benefits of Migration

- **Modern Java 21** features and performance improvements
- **Cloud-ready** microservice architecture
- **Production-ready** with health checks and metrics
- **Maintainable** code with proper structure and tests
- **Future-proof** with Jakarta EE and latest Spring Boot

## 🤝 Contributing

This is a reference migration project. Feel free to:
- Use it as a template for your own migrations
- Adapt the AI prompts for different migration scenarios
- Improve the documentation and examples

## 📄 License

Open source - feel free to use this as a reference for your own projects.

---

**Happy Migrating! 🚀**

For detailed instructions, start with `migrated-code/QUICKSTART.md`
