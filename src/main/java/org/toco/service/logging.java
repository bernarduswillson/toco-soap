package org.toco.service;

import org.toco.entity.logging_entity;
import org.toco.model.logging_model;

public class logging {
    public void addLogging(String description, String IP, String endpoint) {
        logging_entity logging = new logging_entity(description, IP, endpoint);
        logging_model logging_model = new logging_model();
        logging_model.insert(logging);
    }
}
