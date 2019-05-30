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
import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

@Component
@NoArgsConstructor
public final class TaskEditCommand implements ICommand {

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
        return "task-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected task.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Input task id for edit: ");
        @Nullable final String id = terminalService.nextLine();
        @Nullable final TaskDTO task = taskEndpoint.findOneTask(currentSession, id);
        System.out.println("Input new task's name: ");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Input new task's description: ");
        @Nullable final String description = terminalService.nextLine();
        System.out.println("Input task's status(planned, inprocess, done: ");
        @Nullable final String status = terminalService.nextLine();
        task.setName(name);
        task.setDescription(description);
        try {
            task.setStatus(Status.valueOf(status.toUpperCase()));
            task.setDateEnd(null);
            if (task.getStatus().equals(Status.DONE)) {
                task.setDateEnd(DateFormatter.dateToXMLGregorianCalendar(new Date()));
            }
            taskEndpoint.editTask(currentSession, task);
            System.out.println("Task id: " + id + " edit is update!");
        } catch (Exception e) {
            throw new DataValidateException_Exception(e.getMessage());
        }
    }
}
