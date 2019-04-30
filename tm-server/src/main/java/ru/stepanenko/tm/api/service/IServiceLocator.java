package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;

public interface IServiceLocator {

    IProjectService getProjectService();
    ITaskService getTaskService();
    IUserService getUserService();
    ITerminalService getTerminalService();
    ISessionService getSessionService();
    Session getSession();
    void setSession(@NotNull final Session session);
}
