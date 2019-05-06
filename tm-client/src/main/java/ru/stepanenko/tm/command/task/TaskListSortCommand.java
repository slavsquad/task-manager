package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.AuthenticationSecurityException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.Task;
import ru.stepanenko.tm.endpoint.TaskEndpoint;

import java.util.Collection;

@NoArgsConstructor
public class TaskListSortCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-list-sort";
    }

    @Override
    public String getDescription() {
        return "Sorted list task by: order, dateBegin, dateEnd or status";
    }

    @Override
    public void execute() throws AuthenticationSecurityException_Exception {
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Please input how to sort list task(order, dateBegin, dateEnd, status)");
        @NotNull final String comparator = terminalService.nextLine();
        Collection<Task> sortedTasks = taskEndpoint.sortAllTaskByUserId(currentSession, comparator);
        if (sortedTasks != null && !sortedTasks.isEmpty()) {
            System.out.println("List task's sorted by " + comparator + " :");
            sortedTasks.forEach(e -> System.out.println("id: " + e.getId() +
                    " name: " + e.getName() +
                    " description: " + e.getDescription() +
                    " data start: " + e.getDateBegin() +
                    " data end: " + e.getDateEnd() +
                    " status: " + e.getStatus()));
        } else {
            System.out.println("Incorrect input parameter or task list is empty!");
        }
    }
}
