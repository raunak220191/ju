package com.example.hello;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for sayHelloRequest element.
 * Generated from XSD schema.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "name",
    "city",
    "datetime"
})
@XmlRootElement(name = "sayHelloRequest", namespace = "http://example.com/hello")
public class SayHelloRequest {

    @XmlElement(namespace = "http://example.com/hello", required = true)
    protected String name;
    
    @XmlElement(namespace = "http://example.com/hello", required = true)
    protected String city;
    
    @XmlElement(namespace = "http://example.com/hello")
    protected String datetime;

    public String getName() {
        return name;
    }

    public void setName(String value) {
        this.name = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String value) {
        this.datetime = value;
    }
}
