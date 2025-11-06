package com.example.hello;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for sayHelloResponse element.
 * Generated from XSD schema.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "greeting",
    "address"
})
@XmlRootElement(name = "sayHelloResponse", namespace = "http://example.com/hello")
public class SayHelloResponse {

    @XmlElement(namespace = "http://example.com/hello", required = true)
    protected String greeting;
    
    @XmlElement(namespace = "http://example.com/hello")
    protected Address address;

    public String getGreeting() {
        return greeting;
    }

    public void setGreeting(String value) {
        this.greeting = value;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address value) {
        this.address = value;
    }
}
