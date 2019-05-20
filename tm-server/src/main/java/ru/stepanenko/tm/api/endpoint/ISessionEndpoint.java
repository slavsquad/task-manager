package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
public interface ISessionEndpoint {

    @WebMethod
    SessionDTO openSession(
            @WebParam(name = "login") @Nullable final String login,
            @WebParam(name = "password") @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void validateSession(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void validateAdminSession(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void closeSession(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;
}
