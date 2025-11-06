package com.example.hello;

import jakarta.xml.bind.annotation.XmlRegistry;

/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.example.hello package.
 */
@XmlRegistry
public class ObjectFactory {

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes.
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SayHelloRequest }
     */
    public SayHelloRequest createSayHelloRequest() {
        return new SayHelloRequest();
    }

    /**
     * Create an instance of {@link SayHelloResponse }
     */
    public SayHelloResponse createSayHelloResponse() {
        return new SayHelloResponse();
    }

    /**
     * Create an instance of {@link Address }
     */
    public Address createAddress() {
        return new Address();
    }
}
