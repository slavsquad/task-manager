package ru.stepanenko.tm.api.service;

public interface IServiceLocator {

    IProjectService getProjectService();

    ITaskService getTaskService();

    IUserService getUserService();

    ISessionService getSessionService();
}
