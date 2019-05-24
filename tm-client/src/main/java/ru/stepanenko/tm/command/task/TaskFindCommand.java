package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import javax.inject.Inject;

@NoArgsConstructor
public class TaskFindCommand implements AbstractCommand {

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
        return "task-find";
    }

    @Override
    public String getDescription() {
        return "Find task by part of name or description.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Please input part of task's name for search:");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Please input part of task's description for search:");
        @Nullable final String description = terminalService.nextLine();
        System.out.println("Find tasks by part of name '" + name + "' or part of description '" + description + "' :");
        taskEndpoint.findAllTaskByPartOfNameOrDescription(currentSession, name, description)
                .forEach(e -> System.out.println("id: " + e.getId() +
                        " name: " + e.getName() +
                        " description: " + e.getDescription() +
                        " data start: " + e.getDateBegin() +
                        " data end: " + e.getDateEnd() +
                        " status: " + e.getStatus()));
    }
}


