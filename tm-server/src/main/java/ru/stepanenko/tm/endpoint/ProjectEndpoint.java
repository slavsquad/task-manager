package ru.stepanenko.tm.endpoint;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.IServiceLocator;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@WebService
public class ProjectEndpoint implements IProjectEndpoint {

    @NotNull
    private IProjectService projectService;

    @NotNull
    private ISessionService sessionService;

    public ProjectEndpoint(
            @NotNull final IServiceLocator serviceLocator) {
        this.projectService = serviceLocator.getProjectService();
        this.sessionService = serviceLocator.getSessionService();
    }

    @Override
    @WebMethod
    public void createProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "project") @Nullable final ProjectDTO projectDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        projectService.create(projectDTO);
    }

    @Override
    @WebMethod
    public void editProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "project") @Nullable final ProjectDTO projectDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        projectService.edit(projectDTO);
    }

    @Override
    @WebMethod
    public ProjectDTO findOneProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return projectService.findOne(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        projectService.remove(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> findAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return projectService.findAllByUserId(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        projectService.removeAllByUserId(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> sortAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "parameter") @Nullable final String parameter
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return projectService.sortAllByUserId(sessionDTO.getUserId(), parameter);
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> findAllProjectByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "name") @Nullable final String name,
            @WebParam(name = "description") @Nullable final String description
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validate(sessionDTO);
        return projectService.findAllByPartOfNameOrDescription(name, description, sessionDTO.getUserId());
    }
}
