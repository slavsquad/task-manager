package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;

import java.util.Collection;

public interface ISessionService {

    void clear()
            throws DataValidateException;

    SessionDTO findOne(
            @NotNull final String id)
            throws DataValidateException;

    void remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<SessionDTO> findAll()
            throws DataValidateException;

    SessionDTO create(
            @NotNull final UserDTO userDTO)
            throws DataValidateException;

    void validate(
            @Nullable final SessionDTO sessionDTO)
            throws AuthenticationSecurityException, DataValidateException;

    void validateAdmin(
            @Nullable final SessionDTO sessionDTO)
            throws AuthenticationSecurityException, DataValidateException;
}
