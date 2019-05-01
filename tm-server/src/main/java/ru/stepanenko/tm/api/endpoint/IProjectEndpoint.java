package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IProjectEndpoint {

    @WebMethod
    Project createProject(@WebParam(name = "session") @NotNull final Session session,
                          @WebParam(name = "name") @NotNull final String name,
                          @WebParam(name = "description") @NotNull final String description) throws InvalidSessionException;

    @WebMethod
    Project editProject(@WebParam(name = "session") @NotNull final Session session,
                        @WebParam(name = "id") @NotNull final String id,
                        @WebParam(name = "name") @NotNull final String name,
                        @WebParam(name = "description") @NotNull final String description,
                        @WebParam(name = "status") @NotNull final String status) throws InvalidSessionException;

    @WebMethod
    Collection<Project> findAllProjectByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException;

    @WebMethod
    void removeAllProjectByUserId(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException;

    @WebMethod
    Collection<Project> sortAllProjectByUserId(@WebParam(name = "session") @NotNull final Session session,
                                               @WebParam(name = "comparator") @NotNull final String comparator) throws InvalidSessionException;

    @WebMethod
    Collection<Project> findAllProjectByPartOfNameOrDescription(@WebParam(name = "session") @NotNull final Session session,
                                                                @WebParam(name = "name") @NotNull final String name,
                                                                @WebParam(name = "description") @NotNull final String description) throws InvalidSessionException;
}
