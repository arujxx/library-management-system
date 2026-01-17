package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PostgresDB implements IDB {

    private String host;
    private String username;
    private String password;
    private String dbName;

    private Connection connection;

    public PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                return connection;
            }

            String connectionUrl = host + "/" + dbName;
            connection = DriverManager.getConnection(connectionUrl, username, password);
            return connection;

        } catch (SQLException e) {
            System.out.println("Failed to connect to postgres: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void close() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println("Connection close error: " + e.getMessage());
            }
        }
    }
}
