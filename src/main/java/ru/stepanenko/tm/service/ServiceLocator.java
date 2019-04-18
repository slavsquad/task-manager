package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;

public final class ServiceLocator implements IServiceLocator {
    final private IProjectRepository projectRepository = new ProjectRepository();
    final private ITaskRepository taskRepository = new TaskRepository();
    final private IUserRepository userRepository = new UserRepository();
    final private IProjectService projectService = new ProjectService(projectRepository);
    final private ITaskService taskService = new TaskService(taskRepository);
    final private IUserService userService = new UserService(userRepository);

    @Override
    public IProjectService getProjectService() {
        return projectService;
    }

    @Override
    public ITaskService getTaskService() {
        return taskService;
    }

    @Override
    public IUserService getUserService() {
        return userService;
    }
}
