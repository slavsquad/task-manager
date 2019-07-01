package ru.stepanenko.tm.service;

import org.apache.cxf.transport.http.AbstractHTTPDestination;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.FieldConst;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.ws.WebServiceContext;

@Service
public class SessionService implements ISessionService {

    @Autowired
    IUserService userService;

    @Override
    public void
    validate(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        if (session.getAttribute(FieldConst.USER) == null)
            throw new AuthenticationSecurityException("Session is invalid: \nDoes not found logged user! \nPlease re-userLogin!");
    }

    @Override
    public void validateAdmin(
            @Nullable final HttpSession session
    ) throws AuthenticationSecurityException, DataValidateException {
        validate(session);
        @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
        if (!loggedUser.getRole().equals(Role.ADMIN))
            throw new AuthenticationSecurityException("Forbidden action for your role!");

    }

    @Override
    public boolean isUser(@Nullable final HttpSession session) {
        @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
        if (loggedUser == null) return false;
        return true;
    }

    @Override
    public boolean isAdmin(@Nullable HttpSession session) {
        @NotNull final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
        if (loggedUser == null || loggedUser.getRole() != Role.ADMIN) return false;
        return true;
    }

    @Override
    public UserDTO getLoggedUser(@Nullable final HttpSession session
    ) throws AuthenticationSecurityException {
        @Nullable final UserDTO loggedUser = (UserDTO) session.getAttribute(FieldConst.USER);
        return loggedUser;
    }

    @Override
    public void validateEndpointSession(
            @NotNull final SessionDTO sessionDTO
    ) throws DataValidateException {
        userService.findOne(sessionDTO.getUserId());
    }

    @Override
    public void validateEndpointAdminSession(
            @NotNull SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        @NotNull final UserDTO loggedUser = userService.findOne(sessionDTO.getUserId());
        if (loggedUser.getRole() != Role.ADMIN)
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
