package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.config.Bootstrap;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.endpoint.TaskEndpoint;
import ru.stepanenko.tm.endpoint.UserEndpoint;
import ru.stepanenko.tm.util.ConnectionDB;

import java.sql.Connection;
import java.sql.DriverManager;

public class AppServer {

    public static void main(String[] args) {
        @NotNull final Bootstrap bootstrap = new Bootstrap();
        Class[] endpoints = new Class[]{ProjectEndpoint.class, TaskEndpoint.class, UserEndpoint.class, SessionEndpoint.class};
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/tmdb","admin", "admin")) {
            bootstrap.init(endpoints, conn);
        } catch (Exception e) {
            System.out.println("sdfasdfasdfasdfadfasdf");
            e.printStackTrace();
        }
    }
}
