package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.ICommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public class TaskPrintCommand implements ICommand {

    @Inject
    @NotNull
    TaskEndpoint taskEndpoint;

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
        return "task-print";
    }

    @Override
    public String getDescription() {
        return "Print task by id.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
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
