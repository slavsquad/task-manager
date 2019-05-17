package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IUserEndpoint {

    @WebMethod
    User createUser(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password,
            @WebParam(name = "role") @NotNull final String role)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    User changeUserPassword(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "id") @NotNull final String id,
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    User editUserProfile(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    User findUserByLogin(
            @WebParam(name = "session") @Nullable final Session session,
            @WebParam(name = "login") @NotNull final String login)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    User getUserBySession(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<User> findAllUser(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void loadUserData(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void saveUserData(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void loadUserDataJaxbXml(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void saveUserDataJaxbXml(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void loadUserDataFasterXml(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void saveUserDataFasterXml(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void loadUserDataJaxbJSON(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void saveUserDataJaxbJSON(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void loadUserDataFasterJSON(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void saveUserDataFasterJSON(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

}