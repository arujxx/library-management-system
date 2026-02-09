package com.company.data;

public class DBConfig {
    public String host;
    public String username;
    public String password;
    public String dbName;

    public DBConfig(String host, String username, String password, String dbName) {
        this.host = host;
        this.username = username;
        this.password = password;
        this.dbName = dbName;
    }
}
