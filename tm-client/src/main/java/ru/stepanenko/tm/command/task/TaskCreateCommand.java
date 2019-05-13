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
    public void execute() throws AuthenticationSecurityException_Exception, InputDataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input project id: ");
        @NotNull final String id = terminalService.nextLine();
        projectEndpoint.findOneProject(currentSession, id);
        System.out.println("Please input task name: ");
        @NotNull final String name = terminalService.nextLine();
        System.out.println("Please input task description: ");
        @NotNull final String description = terminalService.nextLine();
        taskEndpoint.createTask(currentSession, name, description, id);
        System.out.println("Task " + name + " is create!");
    }
}
