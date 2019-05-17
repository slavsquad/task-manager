package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import java.util.Collection;

public interface ISessionService {

    void clear()
            throws DataValidateException;

    Session findOne(
            @NotNull final String id)
            throws DataValidateException;

    Session remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<Session> findAll()
            throws DataValidateException;

    Session create(
            @NotNull final String userId)
            throws DataValidateException;

    void validate(
            @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;

    void validateAdmin(
            @Nullable final Session session)
            throws AuthenticationSecurityException, DataValidateException;
}
