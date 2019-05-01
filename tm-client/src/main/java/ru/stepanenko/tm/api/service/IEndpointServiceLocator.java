package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.endpoint.*;

public interface IEndpointServiceLocator {
    ProjectEndpoint getProjectEndpoint();
    TaskEndpoint getTaskEndpoint();
    UserEndpoint getUserEndpoint();
    SessionEndpoint getSessionEndpoint();
    ITerminalService getTerminalService();
    Session getSession();
    void setSession(@NotNull final Session session);
}
