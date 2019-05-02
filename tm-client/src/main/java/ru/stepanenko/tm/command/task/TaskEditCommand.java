package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

@NoArgsConstructor
public final class TaskEditCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-edit";
    }

    @Override
    public String getDescription() {
        return "Edit selected task.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Input task id for edit");
        @NotNull final String id = terminalService.nextLine();
        if (taskEndpoint.findOneTask(currentSession, id) != null) {
            System.out.println("Input new task's name: ");
            @NotNull final String name = terminalService.nextLine();
            System.out.println("Input new task's description: ");
            @NotNull final String description = terminalService.nextLine();
            System.out.println("Input task's status(planned, in process, done: ");
            @NotNull final String status = terminalService.nextLine();
            if (taskEndpoint.editTask(currentSession, id, name, description, status) != null) {
                System.out.println("Task id: " + id + "edit is complete!");
            } else {
                System.out.println("Task name or description can't be empty!");
            }
        } else {
            System.out.println("Task id: " + id + " is not found!");
        }
    }
}
