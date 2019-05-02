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
        return "task-createProject";
    }

    @Override
    public String getDescription() {
        return "Create new task.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.print("Please input project id:");
        @NotNull final String id = terminalService.nextLine();
        @Nullable final Project project = projectEndpoint.findOneProject(currentSession, id);
        if (project != null) {
            System.out.println("Please input task name:");
            @NotNull final String name = terminalService.nextLine();
            System.out.println("Please input task description:");
            @NotNull final String description = terminalService.nextLine();
            if (taskEndpoint.createTask(currentSession, name, description, id) != null) {
                System.out.println("Task " + name + " is create!");
            } else {
                System.out.println("Task " + name + " does not create!");
                System.out.println("Task name or description can't be empty!");
            }
        } else {
            System.out.println("Project id: " + id + " does not found!");
        }
    }
}
