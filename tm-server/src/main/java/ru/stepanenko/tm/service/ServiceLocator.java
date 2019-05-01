package ru.stepanenko.tm.service;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.*;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.SessionRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;
public final class ServiceLocator implements IServiceLocator {

    @Getter
    @Setter
    @NotNull
    private Session session = new Session();

    @NotNull
    final private IProjectRepository projectRepository = new ProjectRepository();

    @NotNull
    final private ITaskRepository taskRepository = new TaskRepository();

    @NotNull
    final private IUserRepository userRepository = new UserRepository();

    @NotNull
    final private ISessionRepository sessionRepository = new SessionRepository();

    @Getter
    @NotNull
    final private IProjectService projectService = new ProjectService(projectRepository);

    @Getter
    @NotNull
    final private ITaskService taskService = new TaskService(taskRepository);

    @Getter
    @NotNull
    final private IUserService userService = new UserService(userRepository, projectRepository, taskRepository);

    @Getter
    @NotNull
    final private ITerminalService terminalService = new TerminalService();

    @Getter
    @NotNull
    final private ISessionService sessionService = new SessionService(sessionRepository, userRepository);
}
