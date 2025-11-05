# Sample SOAP Request for Testing

## Using cURL (Unix/Mac/Linux)

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml; charset=utf-8" \
  -H "SOAPAction: sayHelloAction" \
  -d @- << 'EOF'
<?xml version="1.0" encoding="UTF-8"?>
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
EOF
```

## Using cURL (Windows PowerShell)

```powershell
$headers = @{
    "Content-Type" = "text/xml; charset=utf-8"
    "SOAPAction" = "sayHelloAction"
}

$body = @"
<?xml version="1.0" encoding="UTF-8"?>
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
"@

Invoke-WebRequest -Uri "http://localhost:8080/ws" -Method POST -Headers $headers -Body $body
```

## Expected Response

```xml
<?xml version="1.0" encoding="UTF-8"?>
<SOAP-ENV:Envelope xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/">
   <SOAP-ENV:Header/>
   <SOAP-ENV:Body>
      <ns2:sayHelloResponse xmlns:ns2="http://example.com/hello">
         <ns2:greeting>Hello John Doe from Mumbai! Current date/time: 2025-11-05 10:30:00</ns2:greeting>
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

## Test Without DateTime (Auto-generated)

```bash
curl -X POST http://localhost:8080/ws \
  -H "Content-Type: text/xml; charset=utf-8" \
  -H "SOAPAction: sayHelloAction" \
  -d '<?xml version="1.0" encoding="UTF-8"?>
<soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
   <soapenv:Header/>
   <soapenv:Body>
      <hel:sayHelloRequest>
         <hel:name>Jane Smith</hel:name>
         <hel:city>Pune</hel:city>
      </hel:sayHelloRequest>
   </soapenv:Body>
</soapenv:Envelope>'
```

## Using SOAP UI

1. Create a new SOAP project
2. Import WSDL: `http://localhost:8080/ws/hello.wsdl`
3. Double-click on `sayHello` request
4. Fill in the sample values:
   ```xml
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
      <soapenv:Header/>
      <soapenv:Body>
         <hel:sayHelloRequest>
            <hel:name>?</hel:name>
            <hel:city>?</hel:city>
            <hel:datetime>?</hel:datetime>
         </hel:sayHelloRequest>
      </soapenv:Body>
   </soapenv:Envelope>
   ```
5. Click the green play button to send the request

## Using Postman

1. Create a new POST request
2. URL: `http://localhost:8080/ws`
3. Headers:
   - `Content-Type: text/xml; charset=utf-8`
   - `SOAPAction: sayHelloAction`
4. Body (raw XML):
   ```xml
   <?xml version="1.0" encoding="UTF-8"?>
   <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" xmlns:hel="http://example.com/hello">
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
5. Send the request
