package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.endpoint.*;

public interface IEndpointServiceLocator {

    ProjectEndpoint getProjectEndpoint();

    TaskEndpoint getTaskEndpoint();

    UserEndpoint getUserEndpoint();

    SessionEndpoint getSessionEndpoint();

    ITerminalService getTerminalService();

    SessionDTO getSessionDTO();

    void setSessionDTO(@Nullable final SessionDTO sessionDTO);
}
