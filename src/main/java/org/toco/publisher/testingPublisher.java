package org.toco.publisher;
import javax.xml.ws.Endpoint;

public class testingPublisher {
    public static void publish() {
        Endpoint.publish("http://localhost:8080/testing", new org.toco.service.testing());
    }
}
