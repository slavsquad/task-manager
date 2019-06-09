package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.servlet.http.HttpSession;

public interface ISessionService {

    void validateSession(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException;

    void validateAdminSession(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException;

}
