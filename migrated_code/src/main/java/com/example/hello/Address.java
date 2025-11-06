package com.example.hello;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;

/**
 * Java class for Address complex type.
 * Generated from XSD schema.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Address", propOrder = {
    "street",
    "city",
    "state",
    "country"
})
public class Address {

    @XmlElement(namespace = "http://example.com/hello")
    protected String street;
    
    @XmlElement(namespace = "http://example.com/hello")
    protected String city;
    
    @XmlElement(namespace = "http://example.com/hello")
    protected String state;
    
    @XmlElement(namespace = "http://example.com/hello")
    protected String country;

    public String getStreet() {
        return street;
    }

    public void setStreet(String value) {
        this.street = value;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String value) {
        this.city = value;
    }

    public String getState() {
        return state;
    }

    public void setState(String value) {
        this.state = value;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String value) {
        this.country = value;
    }
}
