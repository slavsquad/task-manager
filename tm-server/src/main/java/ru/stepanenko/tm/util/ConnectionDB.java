package ru.stepanenko.tm.util;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    static public Connection create(@NotNull final String url, @NotNull final String user, @NotNull final String password){
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            return conn;
        } catch (Exception e) {
            System.out.println("sdfasdfasdfasdfadfasdf");
            e.printStackTrace();
        }
        return null;
    }
}
