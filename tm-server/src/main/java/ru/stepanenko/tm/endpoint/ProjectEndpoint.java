package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IProjectEndpoint;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import java.util.Collection;

public class ProjectEndpoint implements IProjectEndpoint {
    @NotNull
    private IProjectService projectService;

    @NotNull
    private ISessionService sessionService;

    public ProjectEndpoint(@NotNull final IProjectService projectService, @NotNull final ISessionService sessionService) {
        this.projectService = projectService;
        this.sessionService = sessionService;
    }

    @Override
    public Project createProject(@NotNull Session session, @NotNull String name, @NotNull String description) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return projectService.create(name, description, session.getUserId());
    }

    @Override
    public Project editProject(@NotNull Session session, @NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return projectService.edit(id, name, description, status);
    }

    @Override
    public Collection<Project> findAllProjectByUserId(@NotNull Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return projectService.findAllByUserId(session.getUserId());
    }

    @Override
    public void removeAllProjectByUserId(@NotNull Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) return;
        projectService.removeAllByUserId(session.getUserId());
    }

    @Override
    public Collection<Project> sortAllProjectByUserId(@NotNull Session session, @NotNull String comparator) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return projectService.sortAllByUserId(session.getUserId(), comparator);
    }

    @Override
    public Collection<Project> findAllProjectByPartOfNameOrDescription(@NotNull Session session, @NotNull String name, @NotNull String description) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return projectService.findAllByPartOfNameOrDescription(name, description, session.getUserId());
    }

}
