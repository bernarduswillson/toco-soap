package org.toco.publisher;
import javax.xml.ws.Endpoint;

public class transactionPublisher {
    public static void publish() {
        System.out.println("publishing transaction service");
        Endpoint.publish("http://0.0.0.0:8080/transaction", new org.toco.service.transaction());
        System.out.println("transaction service published");
    }

}




