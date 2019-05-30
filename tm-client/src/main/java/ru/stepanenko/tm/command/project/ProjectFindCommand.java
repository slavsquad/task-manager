package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import java.util.Collection;

@Component
@NoArgsConstructor
public class ProjectFindCommand implements ICommand {

    @NotNull
    @Autowired
    ProjectEndpoint projectEndpoint;

    @NotNull
    @Autowired
    SessionEndpoint sessionEndpoint;

    @NotNull
    @Autowired
    ISessionService sessionService;

    @NotNull
    @Autowired
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
