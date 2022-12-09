package com.allantoledo.application.data.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DefaultConnection {
    private static String url = "jdbc:postgresql://localhost:5432/comandavirtual";
    private static String user = "postgres";
    private static String password = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
