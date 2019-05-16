package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.InputDataValidateException;

import java.util.Collection;

public interface ISessionService {

    void clear()
            throws InputDataValidateException;

    Session findOne(
            @NotNull final String id)
            throws InputDataValidateException;

    Session remove(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<Session> findAll()
            throws InputDataValidateException;

    Session create(
            @NotNull final String userId)
            throws InputDataValidateException;

    void validate(
            @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;

    void validateAdmin(
            @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException;
}
