package com.example.hello_microservice.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import org.springframework.core.io.ClassPathResource;

/**
 * Web Service Configuration for SOAP endpoint.
 * Migrated from javax.* to jakarta.* for Java 21 / Spring Boot 3.x.
 */
@Configuration
public class WebServiceConfig {

    /**
     * Register the MessageDispatcherServlet for SOAP requests.
     * Sets transformWsdlLocations=true as per best practices.
     */
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> soapMessageDispatcherServlet(
            ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    /**
     * WSDL definition bean (stable name "hello" for backwards compatibility).
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
     * XSD Schema bean for WSDL generation.
     */
    @Bean
    public XsdSchema helloSchema() {
        return new SimpleXsdSchema(new ClassPathResource("wsdl/hello.xsd"));
    }
}
