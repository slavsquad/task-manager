package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.Task;
import ru.stepanenko.tm.endpoint.TaskEndpoint;

@NoArgsConstructor
public final class TaskRemoveCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-remove";
    }

    @Override
    public String getDescription() {
        return "Remove task from selected project.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.println("Input task id for remove");
        @NotNull final String id = terminalService.nextLine();
        @Nullable Task task = taskEndpoint.removeTask(currentSession, id);
        if (task != null) {
            System.out.println("Task id: "+id+" has removed!");
        } else {
            System.out.println("Task id: " + id + " is not found!");
        }
    }
}
