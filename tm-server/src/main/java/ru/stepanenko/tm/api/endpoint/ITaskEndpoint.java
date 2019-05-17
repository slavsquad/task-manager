package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface ITaskEndpoint {

    @WebMethod
    Task createTask(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description,
            @WebParam(name = "projectId") @NotNull final String projectId)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Task editTask(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description,
            @WebParam(name = "status") @NotNull final String status)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Task findOneTask(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Task removeTask(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Task> findAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Task> findAllTaskByUserId(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeAllTaskByUserId(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Task> sortAllTaskByUserId(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "comparator") @NotNull final String comparator)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Task> findAllTaskByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description)
            throws AuthenticationSecurityException, DataValidateException;
}
