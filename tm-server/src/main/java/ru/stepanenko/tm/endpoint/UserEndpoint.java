package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
@NoArgsConstructor
public class UserEndpoint implements IUserEndpoint {

    @NotNull
    private IUserService userService;

    @NotNull
    private ISessionService sessionService;

    public UserEndpoint(
            @NotNull final IServiceLocator serviceLocator) {
        this.userService = serviceLocator.getUserService();
        this.sessionService = serviceLocator.getSessionService();
    }

    @Override
    @WebMethod
    public void createUser(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateAdmin(sessionDTO);
        userService.create(userDTO);
    }

    @Override
    @WebMethod
    public void changeUserPassword(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateAdmin(sessionDTO);
        userService.edit(userDTO);
    }

    @Override
    @WebMethod
    public void editUserProfile(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        userService.edit(userDTO);
    }

    @Override
    @WebMethod
    public UserDTO findUserByLogin(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "login") @NotNull final String login)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateAdmin(sessionDTO);
        return userService.findByLogin(login);
    }

    @Override
    @WebMethod
    public UserDTO getUserBySession(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return userService.findOne(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<UserDTO> findAllUser(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO)
            throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateAdmin(sessionDTO);
        return userService.findAll();
    }
}
