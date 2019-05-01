package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.UserNoLoginException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

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
    public void execute() throws UserNoLoginException, InvalidSessionException {
        @NotNull final IProjectService projectService = serviceLocator.getProjectService();
        @NotNull
        User currentUser = serviceLocator.getUserService().getCurrentUser();

        IProjectEndpoint projectEndpoint = new ProjectEndpoint(serviceLocator.getProjectService(), serviceLocator.getSessionService());

        projectEndpoint.removeAllProjectByUserId(serviceLocator.getSession());
//        projectService.removeAllByUserId(currentUser.getId());
        System.out.println("All project is remove!");
    }
}
