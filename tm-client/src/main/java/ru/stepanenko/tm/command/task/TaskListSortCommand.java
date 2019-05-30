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
public class TaskListSortCommand implements ICommand {

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
        return "task-list-sort";
    }

    @Override
    public String getDescription() {
        return "Sorted list task by: order, dateBegin, dateEnd or status";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Please input how to sort list task(order, dateBegin, dateEnd, status)");
        @Nullable final String parameter = terminalService.nextLine();
        System.out.println("List task's sorted by " + parameter + " :");
        taskEndpoint.sortAllTaskByUserId(currentSession, parameter).forEach(e -> System.out.println("id: " + e.getId() +
                " name: " + e.getName() +
                " description: " + e.getDescription() +
                " data start: " + e.getDateBegin() +
                " data end: " + e.getDateEnd() +
                " status: " + e.getStatus()));
    }
}
