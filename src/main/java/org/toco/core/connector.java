package org.toco.core;

import java.sql.*;

public class connector {
    public static Connection connect() {
        String user = "toco";
        String password = "toco";
        String url = "jdbc:mysql://localhost:3306/toco_soap";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded!");
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database");
            return connection; // Return the connection before closing

        } catch (ClassNotFoundException exception) {
            throw new RuntimeException("Error when trying to register the driver", exception);
        } catch (SQLException exception) {
            throw new RuntimeException("Connection Error", exception);
        }
    }
}
