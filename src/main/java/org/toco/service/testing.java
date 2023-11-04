package org.toco.service;

import org.toco.core.connector;

import javax.jws.WebService;
import javax.jws.WebMethod;

@WebService
public class testing {
    @WebMethod
    public String hello(String name) {
        connector conn = new connector();
        conn.connect();
        return "Hello " + name + "!";
    }

}
