package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.util.StringValidator;

import java.util.*;

public final class TaskService extends AbstractEntityService<Task, ITaskRepository> implements ITaskService {

    public TaskService(@NotNull final ITaskRepository taskRepository) {
        super(taskRepository);
    }

    @Override
    public Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID) {
        if (!StringValidator.validate(name, description, projectID, userID)) return null;
        return repository.persist(new Task(name, description, projectID, userID));
    }

    @Override
    public Task edit(@NotNull final String id, @NotNull final String name, @NotNull final String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        @NotNull
        Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        return repository.merge(task);
    }

    @Override
    public void removeAllByProjectId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        repository.removeAllByProjectId(id);
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        repository.removeAllByUserId(id);
    }

    @Override
    public Collection<Task> findAllByProjectID(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByProjectId(id);
    }

    @Override
    public Collection<Task> findAllByUserID(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByUserId(id);
    }
}
