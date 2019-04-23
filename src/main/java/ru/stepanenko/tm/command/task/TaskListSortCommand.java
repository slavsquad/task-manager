package ru.stepanenko.tm.command.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;

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
    public void execute() {
        @NotNull final ITaskService taskService = serviceLocator.getTaskService();
        @NotNull final IUserService userService = serviceLocator.getUserService();
        @NotNull final ITerminalService terminalService = serviceLocator.getTerminalService();
        @Nullable
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            System.out.println("This command available only login user!");
            return;
        }
        if (taskService.findAllByUserId(currentUser.getId()).isEmpty()) {
            System.out.println("List of tasks is empty!");
            return;
        }
        System.out.println("Please input how to sort list task(order, dateStart, dateEnd, status)");
        @NotNull
        String comparator = terminalService.nextLine();
        Collection<Task> sortedTasks = taskService.sortAllByUserId(currentUser.getId(), comparator);
        if (sortedTasks != null) {
            System.out.println("List task's sorted by " + comparator + " :");
            sortedTasks.forEach(System.out::println);
        } else {
            System.out.println("Incorrect input parameter!");
        }
    }
}
