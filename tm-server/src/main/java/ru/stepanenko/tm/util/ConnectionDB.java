package ru.stepanenko.tm.util;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionDB {
    @SneakyThrows
    static public Connection create(@NotNull final Properties properties){
        @NotNull final String url = properties.getProperty("url");
        @NotNull final String user = properties.getProperty("user");
        @NotNull final String password = properties.getProperty("password");
        return DriverManager.getConnection(url, user, password);
    }
}
