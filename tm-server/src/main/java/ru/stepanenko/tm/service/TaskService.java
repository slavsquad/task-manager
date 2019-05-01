package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.ComparatorUtil;
import ru.stepanenko.tm.util.EnumUtil;
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
    public Task edit(@NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status) {
        if (!StringValidator.validate(id, name, description, status)) return null;
        if (EnumUtil.stringToStatus(status) == null) return null;
        @NotNull
        Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(EnumUtil.stringToStatus(status));
        if (Status.DONE == EnumUtil.stringToStatus(status)) {
            task.setDateEnd(new Date());
        } else {
            task.setDateEnd(null);
        }
        return repository.merge(task);
    }

    @Override
    public Task findOne(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.findOne(id, userId);
    }

    @Override
    public Task remove(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.remove(id, userId);
    }

    @Override
    public void removeAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return;
        repository.removeAllByProjectId(id, userId);
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        repository.removeAllByUserId(id);
    }

    @Override
    public Collection<Task> sortAllByUserId(@NotNull String id, @NotNull String comparator) {
        if (!StringValidator.validate(id, comparator)) return null;
        if ("order".equals(comparator)) return findAllByUserId(id);
        if (ComparatorUtil.getTaskComparator(comparator) == null) return null;
        return repository.sortAllByUserId(id, ComparatorUtil.getTaskComparator(comparator));
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        return repository.findAllByPartOfNameOrDescription(name, description, userId);
    }

    @Override
    public Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.findAllByProjectId(id, userId);
    }

    @Override
    public Collection<Task> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByUserId(id);
    }
}
