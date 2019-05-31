package ru.stepanenko.tm.api;

public interface IServiceLocator {

    IProjectService getProjectService();
    ITaskService getTaskService();
    IUserService getUserService();
    ITerminalService getTerminalService();
}
