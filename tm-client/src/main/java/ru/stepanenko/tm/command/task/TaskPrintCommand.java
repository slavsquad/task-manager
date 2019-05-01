package ru.stepanenko.tm.command.task;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import java.io.IOException;

public class TaskPrintCommand extends AbstractCommand {
    @Override
    public String getName() {
        return "task-print";
    }

    @Override
    public String getDescription() {
        return "Print task by id.";
    }

    @Override
    public void execute() throws InvalidSessionException_Exception {
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @NotNull final Session currentSession = endpointServiceLocator.getSession();

        System.out.println("Input task id for print");
        @NotNull final String id = terminalService.nextLine();
        @Nullable Task task = taskEndpoint.findOneTask(currentSession, id);
        if (task != null) {
            System.out.println(task);
        } else {
            System.out.println("Task id: " + id + " is not found!");
        }
    }
}
