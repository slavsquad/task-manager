package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Project;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

@NoArgsConstructor
public final class ProjectRemoveCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-remove";
    }

    @Override
    public String getDescription() {
        return "Remove selected project.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        System.out.print("Please input project ID for remove: ");
        @NotNull
        String id = terminalService.nextLine();
        @Nullable final Project project = projectEndpoint.removeProject(currentSession, id);
        if (project != null) {
            System.out.println("Project " + project.getName() + " is remove!");
        } else {
            System.out.println("Project id: " + id + " does not found!");
        }
    }
}
