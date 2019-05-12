package ru.stepanenko.tm.service;

import jdk.nashorn.internal.ir.IfNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

import java.util.*;

public final class TaskService implements ITaskService {

    @NotNull final ITaskRepository repository;
    public TaskService(@NotNull final ITaskRepository taskRepository) {
        this.repository = taskRepository;
    }

    @Override
    public Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID) {
        if (!StringValidator.validate(name, description, projectID, userID)) return null;
        @NotNull final Task task = new Task(name, description, projectID, userID);
        repository.persist(task);
        return findOne(task.getId());
    }

    @Override
    public Task edit(@NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status) {
        if (!StringValidator.validate(id, name, description, status)) return null;
        if (EnumUtil.stringToStatus(status) == null) return null;
        @NotNull final Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(EnumUtil.stringToStatus(status));
        if (Status.DONE == EnumUtil.stringToStatus(status)) {
            task.setDateEnd(new Date());
        } else {
            task.setDateEnd(null);
        }
        repository.merge(task);
        return findOne(task.getId());
    }

    @Override
    public Task findOne(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.findOne(id, userId);
    }

    @Override
    public Task remove(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        @Nullable final Task task = findOne(id, userId);
        if (task==null) return null;
        repository.remove(id, userId);
        return task;
    }

    @Override
    public void clear() {
        repository.removeAll();
    }

    @Override
    public Task findOne(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findOne(id);
    }

    @Override
    public Task remove(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        @Nullable final Task task = findOne(id);
        if (task==null) return null;
        repository.remove(id);
        return task;
    }

    @Override
    public void recovery(@NotNull Collection<Task> collection) {
        repository.recovery(collection);
    }

    @Override
    public Collection<Task> findAll() {
        return repository.findAll();
    }

    @Override
    public Integer removeAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.removeAllByProjectAndUserId(id, userId);
    }

    @Override
    public Integer removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.removeAllByUserId(id);
    }

    @Override
    public Collection<Task> sortAllByUserId(@NotNull String id, @NotNull String parameter) {
        if (!StringValidator.validate(id, parameter)) return null;
        if (!ParameterValidator.validate(parameter)) return null;
        if ("order".equals(parameter)) return findAllByUserId(id);
        return repository.sortAllByUserId(id, parameter);
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        return repository.findAllByPartOfNameOrDescription(name, description, userId);
    }

    @Override
    public Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.findAllByProjectAndUserId(id, userId);
    }

    @Override
    public Collection<Task> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByUserId(id);
    }
}
