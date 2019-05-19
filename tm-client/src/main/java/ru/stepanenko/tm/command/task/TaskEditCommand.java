package ru.stepanenko.tm.command.task;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITerminalService;
import ru.stepanenko.tm.command.AbstractCommand;
import ru.stepanenko.tm.endpoint.*;

import javax.xml.datatype.DatatypeFactory;
import java.util.Date;
import java.util.GregorianCalendar;

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
    public void execute() throws AuthenticationSecurityException_Exception, DataValidateException_Exception {
        @NotNull final ProjectEndpoint projectEndpoint = endpointServiceLocator.getProjectEndpoint();
        @NotNull final TaskEndpoint taskEndpoint = endpointServiceLocator.getTaskEndpoint();
        @NotNull final ITerminalService terminalService = endpointServiceLocator.getTerminalService();
        @Nullable final SessionDTO currentSession = endpointServiceLocator.getSessionDTO();
        endpointServiceLocator.getSessionEndpoint().validateSession(currentSession);
        System.out.println("Input task id for edit: ");
        @Nullable final String id = terminalService.nextLine();
        @Nullable final TaskDTO task = taskEndpoint.findOneTask(currentSession, id);
        System.out.println("Input new task's name: ");
        @Nullable final String name = terminalService.nextLine();
        System.out.println("Input new task's description: ");
        @Nullable final String description = terminalService.nextLine();
        System.out.println("Input task's status(planned, inprocess, done: ");
        @Nullable final String status = terminalService.nextLine();
        task.setName(name);
        task.setDescription(description);
        try {
            task.setStatus(Status.valueOf(status.toUpperCase()));
            task.setDateEnd(null);
            if (task.getStatus().equals(Status.DONE))
            {
                GregorianCalendar gregorianCalendar = new GregorianCalendar();
                gregorianCalendar.setTime(new Date());
                task.setDateEnd(DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar));
            }
            taskEndpoint.editTask(currentSession, task);
            System.out.println("Task id: " + id + " edit is update!");
        } catch (Exception e){
            throw new DataValidateException_Exception(e.getMessage());
        }
    }
}
