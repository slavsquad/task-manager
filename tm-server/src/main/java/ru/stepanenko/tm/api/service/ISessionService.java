package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;

import java.io.IOException;

public interface ISessionService extends IAbstractEntityService<Session> {

    Session create(@NotNull final String userId) throws IOException;
    boolean validate(@Nullable final Session session) throws AuthenticationSecurityException;
    boolean validateAdmin(@Nullable final Session session) throws AuthenticationSecurityException;
}
