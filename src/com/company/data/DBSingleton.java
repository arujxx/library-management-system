package com.company.data;

import com.company.data.interfaces.IDB;

public class DBSingleton {

    private static IDB instance;

    private DBSingleton() {}

    public static IDB getInstance(DBConfig cfg) {
        if (instance == null) {
            instance = new PostgresDB(
                    cfg.host,
                    cfg.username,
                    cfg.password,
                    cfg.dbName
            );
        }
        return instance;
    }
}
