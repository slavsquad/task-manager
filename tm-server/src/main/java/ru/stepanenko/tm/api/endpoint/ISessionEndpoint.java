package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;

public interface ISessionEndpoint {

    Session createSession(@NotNull final String userId);
}
