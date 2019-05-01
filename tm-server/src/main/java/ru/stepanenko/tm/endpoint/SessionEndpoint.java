package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.entity.Session;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

@WebService
@NoArgsConstructor
public class SessionEndpoint implements ISessionEndpoint {

    @NotNull
    private ISessionService sessionService;

    public SessionEndpoint(@NotNull final IServiceLocator serviceLocator) {
        this.sessionService = serviceLocator.getSessionService();
    }

    @Override
    @WebMethod
    public Session createSession(@WebParam(name = "userId") @NotNull String userId) {
        return sessionService.create(userId);
    }
}
