package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;


@NoArgsConstructor
public final class ProjectCreateCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-createProject";
    }

    @Override
    public String getDescription() {
        return "Create new project.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.println("Please input project name:");
        @NotNull final String name = terminalService.nextLine();
        System.out.println("Please input project description:");
        @NotNull final String description = terminalService.nextLine();
        if (projectEndpoint.createProject(currentSession, name, description) != null) {
            System.out.println("Project " + name + " is createProject!");
        } else {
            System.out.println("Project " + name + " does not createProject!");
            System.out.println("Project name or description does not empty");
        }
    }
}
