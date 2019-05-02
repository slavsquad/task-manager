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
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.print("Please input project ID for print: ");
        @NotNull final String id = terminalService.nextLine();
        @Nullable final Project findProject = projectEndpoint.findOneProject(currentSession, id);
        if (findProject != null) {
            System.out.println("id: " + findProject.getId() +
                    "\nname: " + findProject.getName() +
                    "\ndescription: " + findProject.getDescription() +
                    "\ndata start: " + findProject.getDateStart() +
                    "\ndata end: " + findProject.getDateEnd() +
                    "\nstatus: " + findProject.getStatus());
        } else {
            System.out.println("Project with id: " + id + " does not found!");
        }
    }
}
