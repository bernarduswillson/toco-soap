package org.toco.core;

import java.sql.*;

import io.github.cdimascio.dotenv.Dotenv;

public class connector {
    public static Connection connect() {
        Dotenv dotenv = Dotenv.load();
        String DB_URL = dotenv.get("DB_URL");
        String DB_USERNAME = dotenv.get("DB_USERNAME");
        String DB_PASSWORD = dotenv.get("DB_PASSWORD");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
            System.out.println("Connected to the database");
            return connection; // Return the connection before closing

        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Error when trying to register the driver", exception);
        } catch (SQLException exception) {
            throw new RuntimeException("Connection Error", exception);
        }
    }
}
