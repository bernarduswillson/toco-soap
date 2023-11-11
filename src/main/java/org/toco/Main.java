package org.toco;
import org.toco.publisher.gemsPublisher;
import org.toco.publisher.transactionPublisher;

public class Main {
    public static void main(String[] args) {
        System.out.println("server started");
        gemsPublisher gemspublisher = new gemsPublisher();
        gemspublisher.publish();
        transactionPublisher transactionpublisher = new transactionPublisher();
        transactionpublisher.publish();
        System.out.println("all services online");
    }
}