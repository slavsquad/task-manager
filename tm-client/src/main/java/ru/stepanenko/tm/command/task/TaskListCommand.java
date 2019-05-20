package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import java.util.Collection;

@NoArgsConstructor
public final class TaskListCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-list";
    }

    @Override
    public String getDescription() {
        return "Show all tasks or selected task.";
    }

    @Override
    public void execute(
    ) throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input project id or press ENTER for print all tasks: ");
        @Nullable final String id = terminalService.nextLine();
        if ("".equals(id)) {
            System.out.println("List of tasks:");
            taskEndpoint.findAllTaskByUserId(currentSession)
                    .forEach(e -> System.out.println(
                            "id:" + e.getId() +
                            " name:" + e.getName() +
                            " project_id:" + e.getProjectId()));
            return;
        }
        projectEndpoint.findOneProject(currentSession, id);
        System.out.println("List task for project id:" + id + " is empty!");
        taskEndpoint.findAllTaskByProjectId(currentSession, id)
                .forEach(e -> System.out.println("id:" + e.getId() + " name:" + e.getName()));
    }
}
