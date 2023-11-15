package org.toco;

import org.toco.publisher.service_publisher;
import org.toco.service.mail;


public class Main {
    public static void main(String[] args) {
        System.out.println("server started");
        service_publisher.publish();
        System.out.println("all services online");

    }
}