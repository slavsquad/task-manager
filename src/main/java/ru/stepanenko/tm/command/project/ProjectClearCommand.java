package ru.stepanenko.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.AbstractEntity;
import ru.stepanenko.tm.entity.User;

public final class ProjectClearCommand extends AbstractCommand {
    @NotNull
    private final IProjectService projectService;
    @NotNull
    private final IUserService userService;

    public ProjectClearCommand(@NotNull final IProjectService projectService, @NotNull final IUserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @Override
    public String getName() {
        return "project-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all project.";
    }

    @Override
    public void execute() {
        @NotNull
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        projectService.removeAllByUserId(currentUser.getId());
        System.out.println("All project is remove!");
    }
}
