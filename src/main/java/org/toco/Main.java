package org.toco;
import org.toco.publisher.testingPublisher;
import org.toco.publisher.gemsPublisher;
import org.toco.publisher.testingPublisher;

public class Main {
    public static void main(String[] args) {
        testingPublisher testingpublisher = new testingPublisher();
        testingpublisher.publish();
        gemsPublisher gemspublisher = new gemsPublisher();
        gemspublisher.publish();
        testingPublisher testingpublisher = new testingPublisher();
        testingpublisher.publish();
    }
}