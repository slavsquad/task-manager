package ru.stepanenko.tm;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.config.Bootstrap;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.SessionEndpoint;
import ru.stepanenko.tm.endpoint.TaskEndpoint;
import ru.stepanenko.tm.endpoint.UserEndpoint;

import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;

public class AppServer {
    public static void main(String[] args) {

        Class[] endpoints = new Class[]{ProjectEndpoint.class, TaskEndpoint.class, UserEndpoint.class, SessionEndpoint.class};
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.init(endpoints);
    }
}
