package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.*;
@AllArgsConstructor
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
}
