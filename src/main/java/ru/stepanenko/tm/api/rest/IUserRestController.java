package ru.stepanenko.tm.api.rest;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.servlet.http.HttpSession;

public interface IUserRestController {

    void login(
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final HttpSession session
    ) throws DataValidateException, AuthenticationSecurityException;

    void logout(
            @NotNull final HttpSession session);
}
