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


@Component
@NoArgsConstructor
public class ProjectPrintCommand implements ICommand {

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
