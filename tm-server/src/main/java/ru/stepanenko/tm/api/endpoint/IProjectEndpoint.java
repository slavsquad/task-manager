package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IProjectEndpoint {

    @WebMethod
    ProjectDTO createProject(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "project") @NotNull final ProjectDTO projectDTO)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    ProjectDTO editProject(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "project") @NotNull final ProjectDTO projectDTO)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Project findOneProject(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Project removeProject(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Project> findAllProjectByUserId(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeAllProjectByUserId(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Project> sortAllProjectByUserId(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "parameter") @NotNull final String parameter)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<Project> findAllProjectByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "name") @NotNull final String name,
            @WebParam(name = "description") @NotNull final String description)
            throws AuthenticationSecurityException, DataValidateException;
}
