package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.ITaskEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import java.util.Collection;

public class TaskEndpoint implements ITaskEndpoint {

    @NotNull
    private ISessionService sessionService;

    @NotNull
    private ITaskService taskService;

    public TaskEndpoint(@NotNull ISessionService sessionService, @NotNull ITaskService taskService) {
        this.sessionService = sessionService;
        this.taskService = taskService;
    }

    @Override
    public Task createTask(@NotNull Session session, @NotNull String name, @NotNull String description, @NotNull String projectID) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.create(name, description, projectID, session.getUserId());
    }

    @Override
    public Task editTask(@NotNull Session session, @NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.edit(id, name, description, status);
    }

    @Override
    public Collection<Task> findAllTaskByProjectId(@NotNull Session session, @NotNull String id) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.findAllByProjectId(id);
    }

    @Override
    public Collection<Task> findAllTaskByUserId(@NotNull Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.findAllByUserId(session.getUserId());
    }

    @Override
    public void removeAllTaskByProjectId(@NotNull Session session, @NotNull String id) throws InvalidSessionException {
        if(!sessionService.validate(session)) return;
        taskService.removeAllByProjectId(id);
    }

    @Override
    public void removeAllTaskByUserId(@NotNull Session session) throws InvalidSessionException {
        if(!sessionService.validate(session)) return;
        taskService.removeAllByUserId(session.getUserId());

    }

    @Override
    public Collection<Task> sortAllTaskByUserId(@NotNull Session session, @NotNull String comparator) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.sortAllByUserId(session.getUserId(),comparator);
    }

    @Override
    public Collection<Task> findAllTaskByPartOfNameOrDescription(@NotNull Session session, @NotNull String name, @NotNull String description) throws InvalidSessionException {
        if(!sessionService.validate(session)) return null;
        return taskService.findAllByPartOfNameOrDescription(name,description,session.getUserId());
    }
}
