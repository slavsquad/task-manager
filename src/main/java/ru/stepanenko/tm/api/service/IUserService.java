package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.UserDTO;

import java.util.Collection;

@Service
public interface IUserService {

    @Transactional
    void create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    UserDTO findByLogin(
            @Nullable final String login
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    UserDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void clear(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<UserDTO> findAll(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    UserDTO authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException;
}
