package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

public interface ISessionService extends IAbstractEntityService<Session> {
    Session create(@NotNull final String userId);
    boolean validate (@NotNull final Session session) throws InvalidSessionException;
    boolean validateAdmin(@NotNull final Session session) throws InvalidSessionException;
}
