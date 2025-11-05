#!/bin/bash

# Build and Run Script for Hello World SOAP Microservice

set -e

echo "======================================"
echo "Hello World SOAP Microservice - Java 21"
echo "======================================"
echo ""

# Color codes
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Step 1: Generate JAXB classes
echo -e "${YELLOW}Step 1: Generating JAXB classes from XSD...${NC}"
mvn clean jaxb40:generate

echo -e "${GREEN}✓ JAXB classes generated successfully${NC}"
echo ""

# Step 2: Compile
echo -e "${YELLOW}Step 2: Compiling the project...${NC}"
mvn compile

echo -e "${GREEN}✓ Compilation successful${NC}"
echo ""

# Step 3: Run tests
echo -e "${YELLOW}Step 3: Running tests...${NC}"
mvn test

echo -e "${GREEN}✓ Tests passed${NC}"
echo ""

# Step 4: Package
echo -e "${YELLOW}Step 4: Packaging application...${NC}"
mvn package -DskipTests

echo -e "${GREEN}✓ Application packaged${NC}"
echo ""

# Step 5: Run
echo -e "${YELLOW}Step 5: Starting the microservice...${NC}"
echo ""
echo "The service will be available at:"
echo "  - SOAP Endpoint: http://localhost:8080/ws"
echo "  - WSDL: http://localhost:8080/ws/hello.wsdl"
echo "  - Health Check: http://localhost:8080/actuator/health"
echo ""
echo "Press Ctrl+C to stop the service"
echo ""

mvn spring-boot:run
