package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.FieldConst;

import javax.servlet.http.HttpSession;

public enum SessionService implements ISessionService {

    INSTANCE;

    @Override
    public void validateSession(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        if (session.getAttribute(FieldConst.USER) == null)
            throw new AuthenticationSecurityException("Session is invalid: \nDoes not found logged user! \nPlease re-login!");
    }

    @Override
    public void validateAdminSession(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException {
        validateSession(session);
        @NotNull final User loggedUser = (User) session.getAttribute(FieldConst.USER);
        if (!loggedUser.getRole().equals(Role.ADMIN))
            throw new AuthenticationSecurityException("Forbidden action for your role!");

    }
}