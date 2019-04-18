package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.util.StringValidator;

import java.util.*;

public final class TaskService implements ITaskService {
    private ITaskRepository taskRepository;

    public TaskService(final ITaskRepository ITaskRepository) {
        this.taskRepository = ITaskRepository;
    }

    @Override
    public void clear(final String projectID) {
        Collection<Task> removalTasks = findAllByProjectID(projectID);
        for (Task task : removalTasks) {
            taskRepository.remove(task.getId());
        }
    }

    @Override
    public Task create(final String name, final String description, final String projectID, final String userID) {
        if (!StringValidator.validate(name, description, projectID, userID)) return null;
        return taskRepository.persist(new Task(name, description, projectID, userID));
    }

    @Override
    public Collection<Task> findAllByProjectID(final String id) {
        if (!StringValidator.validate(id)) return null;
        return taskRepository.findAllByProjectId(id);
    }

    @Override
    public Collection<Task> findAllByUserID(final String id) {
        if (!StringValidator.validate(id)) return null;
        return taskRepository.findAllByUserId(id);
    }

    @Override
    public Task remove(final String id) {
        if (!StringValidator.validate(id)) return null;
        return taskRepository.remove(id);
    }

    @Override
    public Task edit(final String id, final String name, final String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        return taskRepository.merge(task);
    }

    @Override
    public Task findOne(final String id) {
        if (!StringValidator.validate(id)) return null;
        return taskRepository.findOne(id);
    }
}
