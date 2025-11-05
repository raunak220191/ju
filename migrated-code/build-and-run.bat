@echo off
REM Build and Run Script for Hello World SOAP Microservice (Windows)

echo ======================================
echo Hello World SOAP Microservice - Java 21
echo ======================================
echo.

REM Step 1: Generate JAXB classes
echo Step 1: Generating JAXB classes from XSD...
call mvn clean jaxb40:generate
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

echo [OK] JAXB classes generated successfully
echo.

REM Step 2: Compile
echo Step 2: Compiling the project...
call mvn compile
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

echo [OK] Compilation successful
echo.

REM Step 3: Run tests
echo Step 3: Running tests...
call mvn test
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

echo [OK] Tests passed
echo.

REM Step 4: Package
echo Step 4: Packaging application...
call mvn package -DskipTests
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

echo [OK] Application packaged
echo.

REM Step 5: Run
echo Step 5: Starting the microservice...
echo.
echo The service will be available at:
echo   - SOAP Endpoint: http://localhost:8080/ws
echo   - WSDL: http://localhost:8080/ws/hello.wsdl
echo   - Health Check: http://localhost:8080/actuator/health
echo.
echo Press Ctrl+C to stop the service
echo.

call mvn spring-boot:run
