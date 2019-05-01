package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.io.IOException;

@WebService
public interface ISessionEndpoint {

    @WebMethod
    Session openSession(@WebParam(name="login") @NotNull final String login,
                        @WebParam(name="password") @NotNull final String password) throws AuthenticationSecurityException, IOException;
    @WebMethod
    Session closeSession(@WebParam(name = "session") @NotNull final Session session) throws InvalidSessionException;
}
