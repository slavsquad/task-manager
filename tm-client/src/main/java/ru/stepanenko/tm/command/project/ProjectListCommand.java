package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public final class ProjectListCommand implements ICommand {

    @Inject
    @NotNull
    ProjectEndpoint projectEndpoint;

    @Inject
    @NotNull
    SessionEndpoint sessionEndpoint;

    @Inject
    @NotNull
    ISessionService sessionService;

    @Override
    public String getName() {
        return "project-list";
    }

    @Override
    public String getDescription() {
        return "Show all project or selected project.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("List of projects:");
        projectEndpoint.findAllProjectByUserId(currentSession)
                .forEach(e -> System.out.println("id: " + e.getId() + " name: " + e.getName()));
    }
}
