package ru.stepanenko.tm.service;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.*;

public final class ServiceLocator implements IServiceLocator {

    @Getter
    @NotNull
    final private IProjectService projectService;

    @Getter
    @NotNull
    final private ITaskService taskService;

    @Getter
    @NotNull
    final private IUserService userService;

    @Getter
    @NotNull
    final private ISessionService sessionService;

    public ServiceLocator(
            @NotNull final IProjectService projectService,
            @NotNull final ITaskService taskService,
            @NotNull final IUserService userService,
            @NotNull final ISessionService sessionService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
        this.sessionService = sessionService;
    }
}
