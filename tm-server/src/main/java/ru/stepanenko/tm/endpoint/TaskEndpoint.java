package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;
@WebService
@NoArgsConstructor
public class TaskEndpoint implements ITaskEndpoint {

    @NotNull
    private ISessionService sessionService;

    @NotNull
    private ITaskService taskService;

    public TaskEndpoint(@NotNull final IServiceLocator serviceLocator) {
        this.sessionService = serviceLocator.getSessionService();
        this.taskService = serviceLocator.getTaskService();
    }

    @Override
    @WebMethod
    public Task createTask(@WebParam(name = "session") @NotNull final Session session,
                           @WebParam(name = "name") @NotNull final String name,
                           @WebParam(name = "description") @NotNull final String description,
                           @WebParam(name = "projectId") @NotNull final String projectId) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.create(name, description, projectId, session.getUserId());
    }

    @Override
    @WebMethod
    public Task editTask(@WebParam(name = "session") @NotNull final Session session,
                         @WebParam(name = "id") @NotNull final String id,
                         @WebParam(name = "name") @NotNull final String name,
                         @WebParam(name = "description") @NotNull final String description,
                         @WebParam(name = "status") @NotNull final String status) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.edit(id, name, description, status);
    }

    @Override
    @WebMethod
    public Collection<Task> findAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                                   @WebParam(name = "id") @NotNull final String id) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.findAllByProjectId(id);
    }

    @Override
    @WebMethod
    public Collection<Task> findAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.findAllByUserId(session.getUserId());
    }

    @Override
    @WebMethod
    public void removeAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                         @WebParam(name = "id") @NotNull final String id) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        taskService.removeAllByProjectId(id);
    }

    @Override
    @WebMethod
    public void removeAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        taskService.removeAllByUserId(session.getUserId());

    }

    @Override
    @WebMethod
    public Collection<Task> sortAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session,
                                                @WebParam(name = "comparator") @NotNull final String comparator) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.sortAllByUserId(session.getUserId(),comparator);
    }

    @Override
    @WebMethod
    public Collection<Task> findAllTaskByPartOfNameOrDescription(@WebParam(name = "session") @NotNull final Session session,
                                                                 @WebParam(name = "name") @NotNull final String name,
                                                                 @WebParam(name = "description") @NotNull final String description) throws InvalidSessionException {
        if(!sessionService.validate(session)) throw new InvalidSessionException("Session is invalid!");
        return taskService.findAllByPartOfNameOrDescription(name,description,session.getUserId());
    }
}
