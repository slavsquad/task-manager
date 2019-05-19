package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import java.util.Collection;

@NoArgsConstructor
public final class TaskClearCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-clear";
    }

    @Override
    public String getDescription() {
        return "Remove all tasks for selected project.";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input project id or press ENTER for remove all tasks: ");
        @Nullable final String id = terminalService.nextLine();
        if ("".equals(id)) {
            taskEndpoint.removeAllTaskByUserId(currentSession);
            System.out.println("All task has removed!");
            return;
        }
        @Nullable final ProjectDTO project = projectEndpoint.findOneProject(currentSession, id);
        @Nullable final Collection<TaskDTO> findTasks = taskEndpoint.findAllTaskByProjectId(currentSession, id);
        if (findTasks == null || findTasks.isEmpty()) {
            System.out.println("List task for project id:" + id + " is empty!");
            return;
        }
        taskEndpoint.removeAllTaskByProjectId(currentSession, id);
        System.out.println("All tasks for project id:" + id + " remove.");
    }
}
