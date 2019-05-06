package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.Project;
import ru.stepanenko.tm.endpoint.ProjectEndpoint;
import ru.stepanenko.tm.endpoint.Session;

import java.util.Collection;

@NoArgsConstructor
public class ProjectListSortCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-list-sort";
    }

    @Override
    public String getDescription() {
        return "Sorted list project by: order, dateBegin, dateEnd or status";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input how to sort list project(order, dateBegin, dateEnd, status)");
        @NotNull final String comparator = terminalService.nextLine();
        Collection<Project> sortedProjects = projectEndpoint.sortAllProjectByUserId(currentSession, comparator);
        if (sortedProjects != null && !sortedProjects.isEmpty()) {
            System.out.println("Project list sorted by " + comparator + " :");
            sortedProjects.forEach(e -> System.out.println("id: " + e.getId() +
                    " name: " + e.getName() +
                    " description: " + e.getDescription() +
                    " data start: " + e.getDateBegin() +
                    " data end: " + e.getDateEnd() +
                    " status: " + e.getStatus()));
        } else {
            System.out.println("Incorrect input parameter or empty list of projects!");
        }
    }
}
