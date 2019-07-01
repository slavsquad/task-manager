package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;

import javax.servlet.http.HttpSession;

@Service
public interface ISessionService {

    void validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException;

    void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException;

    boolean isUser(
            @Nullable final HttpSession session);

    boolean isAdmin(
            @Nullable final HttpSession session);

    UserDTO getLoggedUser(
            @Nullable final HttpSession session) throws AuthenticationSecurityException;

    void validateEndpointSession(
            @NotNull final SessionDTO sessionDTO
    ) throws DataValidateException;

    void validateEndpointAdminSession(
            @NotNull final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;
}
