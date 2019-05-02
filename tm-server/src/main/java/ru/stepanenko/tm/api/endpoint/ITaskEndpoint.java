package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;

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
                    @WebParam(name = "projectId") @NotNull final String projectId) throws AuthenticationSecurityException;

    @WebMethod
    Task editTask(@WebParam(name = "session") @NotNull final Session session,
                  @WebParam(name = "id") @NotNull final String id,
                  @WebParam(name = "name") @NotNull final String name,
                  @WebParam(name = "description") @NotNull final String description,
                  @WebParam(name = "status") @NotNull final String status) throws AuthenticationSecurityException;

    @WebMethod
    Task findOneTask(@WebParam(name = "session") @NotNull final Session session,
                     @WebParam(name = "id") @NotNull final String id) throws AuthenticationSecurityException;

    @WebMethod
    Task removeTask(@WebParam(name = "session") @NotNull final Session session,
                    @WebParam(name = "id") @NotNull final String id) throws AuthenticationSecurityException;

    @WebMethod
    Collection<Task> findAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                            @WebParam(name = "id") @NotNull final String id) throws AuthenticationSecurityException;

    @WebMethod
    Collection<Task> findAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void removeAllTaskByProjectId(@WebParam(name = "session") @NotNull final Session session,
                                  @WebParam(name = "id") @NotNull final String id) throws AuthenticationSecurityException;

    @WebMethod
    void removeAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    Collection<Task> sortAllTaskByUserId(@WebParam(name = "session") @NotNull final Session session,
                                         @WebParam(name = "comparator") @NotNull final String comparator) throws AuthenticationSecurityException;

    @WebMethod
    Collection<Task> findAllTaskByPartOfNameOrDescription(@WebParam(name = "session") @NotNull final Session session,
                                                          @WebParam(name = "name") @NotNull final String name,
                                                          @WebParam(name = "description") @NotNull final String description) throws AuthenticationSecurityException;
}
