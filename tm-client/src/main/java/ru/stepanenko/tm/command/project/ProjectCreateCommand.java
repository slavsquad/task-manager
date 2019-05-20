package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;


@NoArgsConstructor
public final class ProjectCreateCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "project-create";
    }

    @Override
    public String getDescription() {
        return "Create new project.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input project name:");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Please input project description:");
        @Nullable final String description = terminalService.nextLine();
        @Nullable final ProjectDTO project = new ProjectDTO();
        project.setName(name);
        project.setDescription(description);
        project.setUserId(currentSession.getUserId());

        projectEndpoint.createProject(currentSession, project);
        System.out.println("Project " + name + " is createProject!");
    }
}
