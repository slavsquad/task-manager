package ru.stepanenko.tm.command.project;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Project;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

import java.util.Collection;

public class ProjectListSortCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-sort-list";
    }

    @Override
    public String getDescription() {
        return "Sorted list project by: order, dateStart, dateEnd or status";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.println("Please input how to sort list project(order, dateStart, dateEnd, status)");
        @NotNull final String comparator = terminalService.nextLine();
        Collection<Project> sortedProjects = projectEndpoint.sortAllProjectByUserId(currentSession, comparator);
        if (sortedProjects != null && !sortedProjects.isEmpty()) {
            System.out.println("Project list sorted by " + comparator + " :");
            sortedProjects.forEach(System.out::println);
        } else {
            System.out.println("Incorrect input parameter or empty list of projects!");
        }
    }
}
