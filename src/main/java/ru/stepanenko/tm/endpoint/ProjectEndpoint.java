package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import java.util.Collection;

@Controller
@WebService
public class ProjectEndpoint implements IProjectEndpoint {

    @NotNull
    private final IProjectService projectService;

    @NotNull
    private final ISessionService sessionService;

    @Autowired
    public ProjectEndpoint(
            @NotNull final IProjectService projectService,
            @NotNull final ISessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    @Override
    @WebMethod
    public void createProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "project") @Nullable final ProjectDTO projectDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        projectService.create(projectDTO);
    }

    @Override
    @WebMethod
    public void editProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "project") @Nullable final ProjectDTO projectDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        projectService.edit(projectDTO);
    }

    @Override
    @WebMethod
    public ProjectDTO findOneProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        return projectService.findOne(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeProject(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "id") @Nullable final String id
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        projectService.remove(id, sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> findAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        return projectService.findAllByUserId(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public void removeAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        projectService.removeAllByUserId(sessionDTO.getUserId());
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> sortAllProjectByUserId(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "parameter") @Nullable final String parameter
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        return projectService.sortAllByUserId(sessionDTO.getUserId(), parameter);
    }

    @Override
    @WebMethod
    public Collection<ProjectDTO> findAllProjectByPartOfNameOrDescription(
            @WebParam(name = "session") @Nullable final SessionDTO sessionDTO,
            @WebParam(name = "name") @Nullable final String name,
            @WebParam(name = "description") @Nullable final String description
    ) throws AuthenticationSecurityException, DataValidateException {
        sessionService.validateEndpointSession(sessionDTO);
        return projectService.findAllByPartOfNameOrDescription(name, description, sessionDTO.getUserId());
    }
}
