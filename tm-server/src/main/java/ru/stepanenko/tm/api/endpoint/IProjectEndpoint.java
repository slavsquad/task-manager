package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.InputDataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IProjectEndpoint {

    @WebMethod
    Project createProject(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Project editProject(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description,
            @WebParam(name = "status") @NotNull final String status)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Project findOneProject(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Project removeProject(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Collection<Project> findAllProjectByUserId(
            @WebParam(name = "session") @NotNull final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    void removeAllProjectByUserId(
            @WebParam(name = "session") @NotNull final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Collection<Project> sortAllProjectByUserId(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "comparator") @NotNull final String comparator)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Collection<Project> findAllProjectByPartOfNameOrDescription(
            @WebParam(name = "session") @NotNull final Session session,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description)
            throws AuthenticationSecurityException, InputDataValidateException;
}
