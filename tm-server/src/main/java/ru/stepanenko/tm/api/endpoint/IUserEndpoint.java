package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.ForbiddenActionException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface IUserEndpoint {

    @WebMethod
    User createUser(@WebParam(name = "session") @NotNull final Session session,
                    @WebParam(name = "login") @NotNull final String login,
                    @WebParam(name = "password") @NotNull final String password,
                    @WebParam(name = "role") @NotNull final String role) throws InvalidSessionException;

    @WebMethod
    User editUser(@WebParam(name = "session") @NotNull final Session session,
                  @WebParam(name = "id") @NotNull final String id,
                  @WebParam(name = "login") @NotNull final String login,
                  @WebParam(name = "password") @NotNull final String password,
                  @WebParam(name = "role") @NotNull final String role) throws InvalidSessionException;

    @WebMethod
    User findUserByLogin(@WebParam(name = "session") @NotNull final Session session,
                         @WebParam(name = "login") @NotNull final String login) throws InvalidSessionException;

    @WebMethod
    User authenticationUser(@WebParam(name = "login") @NotNull final String login,
                            @WebParam(name = "password") @NotNull final String password) throws InvalidSessionException;

    @WebMethod
    void loadUserData(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void saveUserData(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void loadUserDataJaxbXml(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void saveUserDataJaxbXml(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void loadUserDataFasterXml(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void saveUserDataFasterXml(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void loadUserDataJaxbJSON(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void saveUserDataJaxbJSON(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void loadUserDataFasterJSON(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

    @WebMethod
    void saveUserDataFasterJSON(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException, ForbiddenActionException;

}