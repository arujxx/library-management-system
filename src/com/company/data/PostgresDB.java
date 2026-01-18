package com.company.data;

import com.company.data.interfaces.IDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class PostgresDB implements IDB {

    private final String host;
    private final String username;
    private final String password;
    private final String dbName;

    public PostgresDB(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }

    @Override
    public Connection getConnection() {
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://" + host + ":5432/" + dbName;

            return DriverManager.getConnection(url, username, password);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}



