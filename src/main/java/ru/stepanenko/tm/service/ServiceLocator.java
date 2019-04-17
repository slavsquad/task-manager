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

public class ServiceLocator implements IServiceLocator {
    private IProjectRepository projectRepository = new ProjectRepository();
    private ITaskRepository taskRepository = new TaskRepository();
    private IUserRepository userRepository = new UserRepository();

    private IProjectService projectService = new ProjectService(projectRepository);
    private ITaskService taskService = new TaskService(taskRepository);
    private IUserService userService = new UserService(userRepository);

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
