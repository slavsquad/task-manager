package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public final class ProjectCreateCommand implements ICommand {

    @Inject
    @NotNull
    ProjectEndpoint projectEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Inject
    @NotNull
    ITerminalService terminalService;

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
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
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
