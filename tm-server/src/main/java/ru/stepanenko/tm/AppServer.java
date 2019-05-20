package ru.stepanenko.tm;

import ru.stepanenko.tm.config.Bootstrap;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.endpoint.TaskEndpoint;
import ru.stepanenko.tm.endpoint.UserEndpoint;

import javax.enterprise.inject.se.SeContainerInitializer;
import javax.inject.Inject;

public class AppServer {

    @Inject
    Bootstrap bootstrap;
    public static void main(String[] args) {
        Class[] endpoints = new Class[]{ProjectEndpoint.class, TaskEndpoint.class, UserEndpoint.class, SessionEndpoint.class};
        SeContainerInitializer.newInstance()
                .addPackages(AppServer.class).initialize()
                .select(Bootstrap.class)
                .get()
                .init();
    }
}
