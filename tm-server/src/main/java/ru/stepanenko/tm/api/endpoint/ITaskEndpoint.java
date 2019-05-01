package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;
@WebService
public interface ITaskEndpoint {

    @WebMethod
    Task createTask(@WebParam(name = "session") @NotNull final Session session,
                    @WebParam(name = "name") @NotNull final String name,
                    @WebParam(name = "description") @NotNull final String description,
                    @WebParam(name = "projectId") @NotNull final String projectId) throws InvalidSessionException;

    @WebMethod
    Task editTask(@WebParam(name = "session") @NotNull final Session session,
                  @WebParam(name = "id") @NotNull final String id,
                  @WebParam(name = "name") @NotNull final String name,
                  @WebParam(name = "description") @NotNull final String description,
                  @WebParam(name = "status") @NotNull final String status) throws InvalidSessionException;

    @WebMethod
    Collection<Task> findAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                            @WebParam(name = "id") @NotNull final String id) throws InvalidSessionException;

    @WebMethod
    Collection<Task> findAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException;

    @WebMethod
    void removeAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                  @WebParam(name = "id") @NotNull final String id) throws InvalidSessionException;

    @WebMethod
    void removeAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException;

    @WebMethod
    Collection<Task> sortAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session,
                                         @WebParam(name = "comparator") @NotNull final String comparator) throws InvalidSessionException;

    @WebMethod
    Collection<Task> findAllTaskByPartOfNameOrDescription(@WebParam(name = "session") @NotNull final Session session,
                                                          @WebParam(name = "name") @NotNull final String name,
                                                          @WebParam(name = "description") @NotNull final String description) throws InvalidSessionException;
}
