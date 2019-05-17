package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
@NoArgsConstructor
public class SessionEndpoint implements ISessionEndpoint {

    @NotNull
    private ISessionService sessionService;

    @NotNull
    private IUserService userService;

    public SessionEndpoint(
            @NotNull final IServiceLocator serviceLocator) {
        this.sessionService = serviceLocator.getSessionService();
        this.userService = serviceLocator.getUserService();
    }

    @Override
    @WebMethod
    public Session openSession(
            @WebParam(name = "login") @NotNull final String login,
            @WebParam(name = "password") @NotNull final String password)
            throws AuthenticationSecurityException, DataValidateException {
        @NotNull
        User loggedUser = userService.authenticationUser(login, password);
        return sessionService.create(loggedUser.getId());
    }

    @Override
    @WebMethod
    public void validateSession(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(session);
    }

    @Override
    @WebMethod
    public void validateAdminSession(
            @WebParam(name = "session") @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateAdmin(session);
    }

    @Override
    @WebMethod
    public Session closeSession(
            @WebParam(name = "session") @NotNull final Session session)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(session);
        return sessionService.remove(session.getId());
    }

}
