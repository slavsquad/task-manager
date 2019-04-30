package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.ISessionEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.entity.Session;

public class SessionEndpoint implements ISessionEndpoint {

    @NotNull
    private ISessionService sessionService;

    public SessionEndpoint(@NotNull ISessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    public Session createSession(@NotNull String userId) {
        return sessionService.create(userId);
    }
}
