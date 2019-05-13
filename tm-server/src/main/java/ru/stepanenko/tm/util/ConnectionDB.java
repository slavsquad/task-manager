package ru.stepanenko.tm.util;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionDB {
    @SneakyThrows
    static public Connection create(@NotNull final String url,
                                    @NotNull final String user,
                                    @NotNull final String password) {
        return DriverManager.getConnection(url, user, password);
    }
}
