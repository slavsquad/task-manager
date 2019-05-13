package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import java.util.Collection;

@NoArgsConstructor
public final class ProjectListCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-list";
    }

    @Override
    public String getDescription() {
        return "Show all project or selected project.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("List of projects:");
        @NotNull final Collection<Project> findProjects = projectEndpoint.findAllProjectByUserId(currentSession);
        if (findProjects != null && !findProjects.isEmpty()) {
            findProjects.forEach(e -> System.out.println("id: " + e.getId() + " name: " + e.getName()));
        } else {
            System.out.println("List of project is empty!");
        }
    }
}
