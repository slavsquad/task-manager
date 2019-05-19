package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public final class TaskCreateCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-create";
    }

    @Override
    public String getDescription() {
        return "Create new task.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
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
