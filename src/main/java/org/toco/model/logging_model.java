package org.toco.model;

import java.sql.*;

import org.toco.core.connector;
import org.toco.entity.logging_entity;

public class logging_model {
    public void insert(logging_entity logging_entity) {
        String sql = "INSERT INTO logging (description, IP, endpoint) VALUES (?, ?, ?)";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, logging_entity.getDescription());
            command.setString(2, logging_entity.getIP());
            command.setString(3, logging_entity.getEndpoint());
            command.execute();
        } catch (SQLException exception) {
            throw new RuntimeException("Error when inserting", exception);
        }
    }
}
