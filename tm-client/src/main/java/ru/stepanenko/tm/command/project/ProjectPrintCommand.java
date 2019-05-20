package ru.stepanenko.tm.command.project;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;


@NoArgsConstructor
public class ProjectPrintCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "project-print";
    }

    @Override
    public String getDescription() {
        return "Print project by id.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
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
