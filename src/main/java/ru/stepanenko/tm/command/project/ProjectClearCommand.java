package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.User;

@NoArgsConstructor
public final class ProjectClearCommand extends AbstractCommand {
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
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull
        User currentUser = serviceLocator.getUserService().getCurrentUser();

        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        projectService.removeAllByUserId(currentUser.getId());
        System.out.println("All project is remove!");
    }
}
