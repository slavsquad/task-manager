package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Service
public interface IUserService {

    void create(
            @Nullable final User user
    ) throws DataValidateException;

    void edit(
            @Nullable final User user
    ) throws DataValidateException;

    User findByLogin(
            @Nullable final String login
    ) throws DataValidateException;

    User findOne(
            @Nullable final String id
    ) throws DataValidateException;

    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    void clear(
    ) throws DataValidateException;

    Collection<User> findAll(
    ) throws DataValidateException;

    User authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException;
}
