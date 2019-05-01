package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

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
    public void execute() throws InvalidSessionException_Exception {
        @NotNull
        final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull
        final Session currentSession = endpointServiceLocator.getSession();

        projectEndpoint.removeAllProjectByUserId(currentSession);
        System.out.println("All project had removed!");
    }
}
