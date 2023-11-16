package org.toco.model;

import org.toco.core.connector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class api_model {
    public Boolean checkApiKey(String apikey) {
        String sql = "select * from api where api_key = (?)";
        try (Connection connection = connector.connect();
                PreparedStatement command = connection.prepareStatement(sql)) {
            command.setString(1, apikey);
            command.execute();
            if (command.getResultSet().next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException exception) {
            throw new RuntimeException("Error when checking apikey", exception);
        }
    }

}
