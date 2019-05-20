package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import java.util.Collection;

public interface IUserService {

    void create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException;

    void edit(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException;

    UserDTO findByLogin(
            @Nullable final String login
    ) throws DataValidateException;

    void clear(
    ) throws DataValidateException;

    UserDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<UserDTO> findAll(
    ) throws DataValidateException;

    UserDTO authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException;
}
