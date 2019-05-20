package ru.stepanenko.tm.api.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface IUserEndpoint {

    @WebMethod
    void createUser(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void changeUserPassword(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void editUserProfile(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "user") @Nullable final UserDTO userDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    UserDTO findUserByLogin(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "login") @Nullable final String login
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    UserDTO getUserBySession(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<UserDTO> findAllUser(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeOneUser(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException;
}