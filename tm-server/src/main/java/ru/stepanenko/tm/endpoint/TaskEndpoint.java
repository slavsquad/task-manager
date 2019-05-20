package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.inject.Inject;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
@NoArgsConstructor
public class TaskEndpoint implements ITaskEndpoint {

    @Inject
    @NotNull
    private ISessionService sessionService;

    @Inject
    @NotNull
    private ITaskService taskService;

    @Override
    @WebMethod
    public void createTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "task") @Nullable final TaskDTO taskDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        taskService.create(taskDTO);
    }

    @Override
    @WebMethod
    public void editTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "task") @Nullable final TaskDTO taskDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        taskService.edit(taskDTO);
    }

    @Override
    @WebMethod
    public TaskDTO findOneTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return taskService.findOne(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeTask(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        taskService.remove(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<TaskDTO> findAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return taskService.findAllByProjectId(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<TaskDTO> findAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return taskService.findAllByUserId(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeAllTaskByProjectId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        taskService.removeAllByProjectId(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        taskService.removeAllByUserId(sessionDTO.getUserId());

    }

    @Override
    @WebMethod
    public Collection<TaskDTO> sortAllTaskByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "comparator") @Nullable final String comparator
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return taskService.sortAllByUserId(sessionDTO.getUserId(), comparator);
    }

    @Override
    @WebMethod
    public Collection<TaskDTO> findAllTaskByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "name") @Nullable final String name,
            @WebParam(name = "description") @Nullable final String description
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return taskService.findAllByPartOfNameOrDescription(name, description, sessionDTO.getUserId());
    }
}
