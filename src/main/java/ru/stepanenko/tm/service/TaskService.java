package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.util.ComparatorUtil;
import ru.stepanenko.tm.util.DataValidator;

import java.util.Collection;

@Service
public class TaskService implements ITaskService {

    @NotNull
    private final ITaskRepository taskRepository;

    @Autowired
    public TaskService(
            @NotNull final ITaskRepository taskRepository
    ) {
        this.taskRepository = taskRepository;
    }

    @Override
    public void create(
            @Nullable final Task task
    ) throws DataValidateException {
        DataValidator.validateTask(task);
        taskRepository.persist(task);
    }

    @Override
    public void edit(
            @Nullable final Task task
    ) throws DataValidateException {
        DataValidator.validateTask(task);
        taskRepository
                .merge(task);
    }

    @Override
    public Task findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Task task = taskRepository
                .findOneByUserId(id, userId);
        if (task == null) throw new DataValidateException("Task not found");
        return task;
    }

    @Override
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Task task = taskRepository
                .findOneByUserId(id, userId);
        if (task == null) throw new DataValidateException("Task not found");
        taskRepository.remove(task.getId());
    }

    @Override
    public void clear(
    ) throws DataValidateException {
        taskRepository.removeAll();
    }

    @Override
    public Task findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Task task = taskRepository
                .findOne(id);
        if (task == null) throw new DataValidateException("Task not found");
        return task;
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Task task = taskRepository
                .findOne(id);
        if (task == null) throw new DataValidateException("Task not found");
        taskRepository.remove(task.getId());
    }

    @Override
    public Collection<Task> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<Task> tasks = taskRepository
                .findAll();
        if (tasks == null) throw new DataValidateException("Tasks not found");
        return tasks;
    }

    @Override
    public void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        taskRepository
                .removeAllByProjectAndUserId(id, userId);
    }

    @Override
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        taskRepository
                .removeAllByUserId(id);

    }

    @Override
    public Collection<Task> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException {
        DataValidator.validateString(id, parameter);
        DataValidator.validateParameter(parameter);
        @Nullable Collection<Task> tasks = taskRepository.sortAllByUserId(id, ComparatorUtil.getComparator(parameter));
        if (tasks == null) throw new DataValidateException("Tasks not found");
        return tasks;
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(name, description, userId);
        @Nullable final Collection<Task> tasks = taskRepository
                .findAllByPartOfNameOrDescription(name, description, userId);
        if (tasks == null) throw new DataValidateException("Tasks not found");
        return tasks;
    }

    @Override
    public Collection<Task> findAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Collection<Task> tasks = taskRepository
                .findAllByProjectAndUserId(id, userId);
        if (tasks == null) throw new DataValidateException("Tasks not found");
        return tasks;
    }

    @Override
    public Collection<Task> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Collection<Task> tasks = taskRepository
                .findAllByUserId(id);
        if (tasks == null) throw new DataValidateException("Tasks not found");
        return tasks;
    }

}
