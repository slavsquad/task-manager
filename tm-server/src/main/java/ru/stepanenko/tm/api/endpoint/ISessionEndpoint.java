package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.InputDataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ISessionEndpoint {

    @WebMethod
    Session openSession(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    void validateSession(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    void validateAdminSession(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;

    @WebMethod
    Session closeSession(
            @WebParam(name = "session") @NotNull final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;
}
