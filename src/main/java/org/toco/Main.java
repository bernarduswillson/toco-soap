package org.toco;
import org.toco.publisher.testingPublisher;
import org.toco.publisher.gemsPublisher;
import org.toco.publisher.transactionPublisher;

public class Main {
    public static void main(String[] args) {
        testingPublisher testingpublisher = new testingPublisher();
        testingpublisher.publish();
        gemsPublisher gemspublisher = new gemsPublisher();
        gemspublisher.publish();
        transactionPublisher transactionpublisher = new transactionPublisher();
        transactionpublisher.publish();

    }
}