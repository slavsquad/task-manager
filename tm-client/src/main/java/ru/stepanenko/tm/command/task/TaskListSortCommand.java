package ru.stepanenko.tm.command.task;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.InvalidSessionException_Exception;
import ru.stepanenko.tm.endpoint.Session;
import ru.stepanenko.tm.endpoint.Task;
import ru.stepanenko.tm.endpoint.TaskEndpoint;

import java.util.Collection;

public class TaskListSortCommand extends AbstractCommand {

    @Override
    public String getName() {
        return "task-list-sort";
    }

    @Override
    public String getDescription() {
        return "Sorted list task by: order, dateStart, dateEnd or status";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();
        System.out.println("Please input how to sort list task(order, dateStart, dateEnd, status)");
        @NotNull final String comparator = terminalService.nextLine();
        Collection<Task> sortedTasks = taskEndpoint.sortAllTaskByUserId(currentSession, comparator);
        if (sortedTasks != null) {
            System.out.println("List task's sorted by " + comparator + " :");
            sortedTasks.forEach(System.out::println);
        } else {
            System.out.println("Incorrect input parameter!");
        }
    }
}
