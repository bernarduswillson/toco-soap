package org.toco.entity;

import lombok.Data;

@Data
public class logging_entity {
    private String description;
    private String IP;
    private String endpoint;

    public logging_entity(String description, String IP, String endpoint) {
        this.description = description;
        this.IP = IP;
        this.endpoint = endpoint;
    }
}
