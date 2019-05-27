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
public class ProjectPrintCommand implements ICommand {
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
        return "project-print";
    }

    @Override
    public String getDescription() {
        return "Print project by id.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Please input project ID for print: ");
        @Nullable final String id = terminalService.nextLine();
        @NotNull final ProjectDTO findProject = projectEndpoint.findOneProject(currentSession, id);
        System.out.println("id: " + findProject.getId() +
                "\nname: " + findProject.getName() +
                "\ndescription: " + findProject.getDescription() +
                "\ndata start: " + findProject.getDateBegin() +
                "\ndata end: " + findProject.getDateEnd() +
                "\nstatus: " + findProject.getStatus());

    }
}
