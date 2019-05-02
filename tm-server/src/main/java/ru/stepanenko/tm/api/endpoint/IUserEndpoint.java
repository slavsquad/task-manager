package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IUserEndpoint {

    @WebMethod
    User createUser(@WebParam(name = "session") @NotNull final Session session,
                    @WebParam(name = "login") @NotNull final String login,
                    @WebParam(name = "password") @NotNull final String password,
                    @WebParam(name = "role") @NotNull final String role) throws AuthenticationSecurityException;

    @WebMethod
    User changeUserPassword(@WebParam(name = "session") @NotNull final Session session,
                            @WebParam(name = "id") @NotNull final String id,
                            @WebParam(name = "login") @NotNull final String login,
                            @WebParam(name = "password") @NotNull final String password,
                            @WebParam(name = "role") @NotNull final String role) throws AuthenticationSecurityException;

    @WebMethod
    User editUserProfile(@WebParam(name = "session") @NotNull final Session session,
                         @WebParam(name = "login") @NotNull final String login,
                         @WebParam(name = "password") @NotNull final String password) throws AuthenticationSecurityException;

    @WebMethod
    User findUserByLogin(@WebParam(name = "session") @NotNull final Session session,
                         @WebParam(name = "login") @NotNull final String login) throws AuthenticationSecurityException;

    @WebMethod
    User getUserBySession(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    Collection<User> findAllUser(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void loadUserData(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void saveUserData(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void loadUserDataJaxbXml(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void saveUserDataJaxbXml(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void loadUserDataFasterXml(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void saveUserDataFasterXml(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void loadUserDataJaxbJSON(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void saveUserDataJaxbJSON(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void loadUserDataFasterJSON(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

    @WebMethod
    void saveUserDataFasterJSON(@WebParam(name = "session") @NotNull final Session session) throws AuthenticationSecurityException;

}