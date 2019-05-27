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
public final class TaskRemoveCommand implements ICommand {

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
        return "task-remove";
    }

    @Override
    public String getDescription() {
        return "Remove task from selected project.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {

        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Input task id for remove: ");
        @Nullable final String id = terminalService.nextLine();
        taskEndpoint.removeTask(currentSession, id);
        System.out.println("Task id: " + id + " has removed!");
    }
}
