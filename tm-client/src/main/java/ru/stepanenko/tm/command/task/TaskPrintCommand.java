package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public class TaskPrintCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "task-print";
    }

    @Override
    public String getDescription() {
        return "Print task by id.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Input task id for print: ");
        @Nullable final String id = terminalService.nextLine();
        @Nullable TaskDTO task = taskEndpoint.findOneTask(currentSession, id);
        System.out.println("id: " + task.getId() +
                "\nname: " + task.getName() +
                "\ndescription: " + task.getDescription() +
                "\ndata start: " + task.getDateBegin() +
                "\ndata end: " + task.getDateEnd() +
                "\nstatus: " + task.getStatus());
    }
}
