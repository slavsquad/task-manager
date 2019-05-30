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
public final class TaskCreateCommand implements ICommand {

    @NotNull
    @Autowired
    ProjectEndpoint projectEndpoint;

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
        return "task-create";
    }

    @Override
    public String getDescription() {
        return "Create new task.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @Nullable final SessionDTO currentSession = sessionService.getCurrentSession();
        sessionEndpoint.validateSession(currentSession);
        System.out.println("Please input project id: ");
        @Nullable final String id = terminalService.nextLine();
        projectEndpoint.findOneProject(currentSession, id);
        System.out.println("Please input task name: ");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Please input task description: ");
        @Nullable final String description = terminalService.nextLine();
        @Nullable final TaskDTO task = new TaskDTO();
        task.setName(name);
        task.setDescription(description);
        task.setProjectId(id);
        task.setUserId(currentSession.getUserId());
        taskEndpoint.createTask(currentSession, task);
        System.out.println("Task " + name + " is create!");
    }
}
