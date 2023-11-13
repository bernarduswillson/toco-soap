package org.toco.publisher;

import javax.xml.ws.Endpoint;

public class service_publisher {
    public static void publish() {
        System.out.println("publishing service");
        Endpoint.publish("http://0.0.0.0:8080/service", new org.toco.service.toco_service_impl());
        System.out.println("service published");
    }
}
