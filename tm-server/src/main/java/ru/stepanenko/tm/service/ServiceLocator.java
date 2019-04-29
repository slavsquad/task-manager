package ru.stepanenko.tm.service;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.*;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.SessionRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;

@Getter
public final class ServiceLocator implements IServiceLocator {

    @NotNull
    final private IProjectRepository projectRepository = new ProjectRepository();
    @NotNull
    final private ITaskRepository taskRepository = new TaskRepository();
    @NotNull
    final private IUserRepository userRepository = new UserRepository();
    @NotNull
    final private ISessionRepository sessionRepository = new SessionRepository();
    @NotNull
    final private IProjectService projectService = new ProjectService(projectRepository);
    @NotNull
    final private ITaskService taskService = new TaskService(taskRepository);
    @NotNull
    final private IUserService userService = new UserService(userRepository, projectRepository, taskRepository);
    @NotNull
    final private ITerminalService terminalService = new TerminalService();
    @NotNull
    final private ISessionService sessionService = new SessionService(sessionRepository,this);
}
