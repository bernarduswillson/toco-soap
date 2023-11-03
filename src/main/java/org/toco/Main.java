package org.toco;
import org.toco.publisher.testingPublisher;
import org.toco.publisher.gemsPublisher;

public class Main {
    public static void main(String[] args) {
        testingPublisher testingpublisher = new testingPublisher();
        testingpublisher.publish();
        gemsPublisher gemspublisher = new gemsPublisher();
        gemspublisher.publish();
    }
}