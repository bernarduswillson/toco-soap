package org.toco.publisher;
import javax.xml.ws.Endpoint;

public class gemsPublisher {
    public static void publish() {
        Endpoint.publish("http://localhost:8080/gems", new org.toco.service.userGems());
    }
}
