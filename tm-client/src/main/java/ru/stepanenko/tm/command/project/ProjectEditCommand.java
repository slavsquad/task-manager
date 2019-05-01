package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

@NoArgsConstructor
public final class ProjectEditCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected project.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.print("Please input project ID for edit: ");
        @NotNull final String id = terminalService.nextLine();
        if (projectEndpoint.findOneProject(currentSession, id) != null) {
            System.out.println("Input new project's name: ");
            @NotNull final String name = terminalService.nextLine();
            System.out.println("Input new project's description: ");
            @NotNull final String description = terminalService.nextLine();
            System.out.println("Input project's status(planned, in process, done): ");
            @NotNull final String status = terminalService.nextLine();
            if (projectEndpoint.editProject(currentSession, id, name, description, status) != null) {
                System.out.println("Project " + name + " is update!");
            } else {
                System.out.println("Project name, description or status can't be empty!");
            }
        } else {
            System.out.println("Project id: " + id + " does not found!");
        }
    }
}
