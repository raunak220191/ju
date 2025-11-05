package com.example.hello_ws.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 * Web Service Configuration for SOAP endpoints
 * Configures the MessageDispatcherServlet and WSDL generation
 */
@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

    /**
     * Registers the MessageDispatcherServlet for handling SOAP requests
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * Exposes the WSDL at: http://localhost:8080/ws/hello.wsdl
     */
    @Bean(name = "hello")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema helloSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("HelloWorldPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://example.com/hello");
        wsdl11Definition.setSchema(helloSchema);
        return wsdl11Definition;
    }

    /**
     * Loads the XSD schema for request/response validation
     */
    @Bean
    public XsdSchema helloSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/hello.xsd"));
    }
}
