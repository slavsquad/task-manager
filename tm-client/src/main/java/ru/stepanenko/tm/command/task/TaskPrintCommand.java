package ru.stepanenko.tm.command.task;

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
public class TaskPrintCommand implements ICommand {

    @NotNull
    @Autowired
    TaskEndpoint taskEndpoint;

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
