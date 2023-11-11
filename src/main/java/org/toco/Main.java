package org.toco;
import org.toco.publisher.gemsPublisher;
import org.toco.publisher.transactionPublisher;

public class Main {
    public static void main(String[] args) {
        System.out.println("server started");
        gemsPublisher.publish();
        transactionPublisher.publish();
        System.out.println("all services online");
    }
}