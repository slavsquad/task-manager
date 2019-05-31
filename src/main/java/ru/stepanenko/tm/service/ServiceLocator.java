package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.repository.IProjectRepository;
import ru.stepanenko.tm.repository.ITaskRepository;
import ru.stepanenko.tm.repository.IUserRepository;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;

public final class ServiceLocator implements IServiceLocator {
    @NotNull
    final private IProjectRepository projectRepository = new ProjectRepository();
    @NotNull
    final private ITaskRepository taskRepository = new TaskRepository();
    @NotNull
    final private IUserRepository userRepository = new UserRepository();
    @NotNull
    final private IProjectService projectService = new ProjectService(projectRepository);
    @NotNull
    final private ITaskService taskService = new TaskService(taskRepository);
    @NotNull
    final private IUserService userService = new UserService(userRepository);
    @NotNull
    final private ITerminalService terminalService = new TerminalService();

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

    @Override
    public ITerminalService getTerminalService() {
        return terminalService;
    }


}
