package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;

import java.util.Collection;

@Service
public interface ISessionService {

    @Transactional
    void clear(

    ) throws DataValidateException;

    @Transactional(readOnly = true)
    SessionDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<SessionDTO> findAll(
    ) throws DataValidateException;

    @Transactional
    SessionDTO create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    void validate(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @Transactional(readOnly = true)
    void validateAdmin(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;
}
