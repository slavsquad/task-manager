package ru.stepanenko.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Project;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

import java.util.Collection;

public class ProjectFindCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-find";
    }

    @Override
    public String getDescription() {
        return "Find project by part of name or description.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.println("Please input part of project's name for search:");
        @NotNull final String name = terminalService.nextLine();
        System.out.println("Please input part of project's description for search:");
        @NotNull String description = terminalService.nextLine();

        @NotNull final Collection<Project> findProjects = projectEndpoint.findAllProjectByPartOfNameOrDescription(currentSession, name, description);
        if (findProjects != null && !findProjects.isEmpty()) {
            System.out.println("Find projects by part of name '" + name + "' or part of description '" + description + "' :");
            findProjects.forEach(System.out::println);
        } else {
            System.out.println("Projects does not found!");
        }
    }
}
