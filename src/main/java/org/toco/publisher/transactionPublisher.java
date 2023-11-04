package org.toco.publisher;
import javax.xml.ws.Endpoint;

public class transactionPublisher {
    public static void publish() {
        Endpoint.publish("http://localhost:8080/transaction", new org.toco.service.transaction());
    }

}
