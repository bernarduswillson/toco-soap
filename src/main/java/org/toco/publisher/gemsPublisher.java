package org.toco.publisher;
import javax.xml.ws.Endpoint;

public class gemsPublisher {
    public static void publish() {
        System.out.println("publishing gems service");
        Endpoint.publish("http://0.0.0.0:8080/gems", new org.toco.service.userGems());
        System.out.println("gems service published");
    }
}
