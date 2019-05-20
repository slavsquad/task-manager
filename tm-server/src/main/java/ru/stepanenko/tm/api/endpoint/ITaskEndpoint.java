package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public interface ITaskEndpoint {

    @WebMethod
    void createTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "task") @Nullable final TaskDTO taskDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void editTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "task") @Nullable final TaskDTO taskDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    TaskDTO findOneTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<TaskDTO> findAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<TaskDTO> findAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    void removeAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<TaskDTO> sortAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "parameter") @Nullable final String parameter
    ) throws AuthenticationSecurityException, DataValidateException;

    @WebMethod
    Collection<TaskDTO> findAllTaskByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "name") @Nullable final String name,
            @WebParam(name = "description") @Nullable final String description
    ) throws AuthenticationSecurityException, DataValidateException;
}
