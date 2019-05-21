package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;
import java.util.Collection;

@NoArgsConstructor
public class ProjectFindCommand implements AbstractCommand {

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
        return "project-find";
    }

    @Override
    public String getDescription() {
        return "Find project by part of name or description.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Please input part of project's name for search:");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Please input part of project's description for search:");
        @Nullable String description = terminalService.nextLine();
        @Nullable final Collection<ProjectDTO> findProjects = projectEndpoint.findAllProjectByPartOfNameOrDescription(currentSession, name, description);
        System.out.println("Find projects by part of name '" + name + "' or part of description '" + description + "' :");
        findProjects.forEach(e -> System.out.println("id: " + e.getId() +
                " name: " + e.getName() +
                " description: " + e.getDescription() +
                " data start: " + e.getDateBegin() +
                " data end: " + e.getDateEnd() +
                " status: " + e.getStatus()));
    }
}
