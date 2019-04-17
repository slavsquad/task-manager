package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.util.StringValidator;

import java.util.*;

public class TaskService implements ITaskService {
    private ITaskRepository ITaskRepository;

    public TaskService(ITaskRepository ITaskRepository) {
        this.ITaskRepository = ITaskRepository;
    }

    @Override
    public void clear(String projectID) {
        Collection<Task> removalTasks = findAllByProjectID(projectID);
        for (Task task : removalTasks) {
            ITaskRepository.remove(task.getId());
        }
    }

    @Override
    public Task create(String name, String description, String projectID) {
        if (!StringValidator.validate(name, description, projectID)) return null;
        return ITaskRepository.persist(new Task(name, description, projectID));
    }

    @Override
    public Collection<Task> findAllByProjectID(String projectID) {
        if (!StringValidator.validate(projectID)) return null;
        Collection<Task> tasks = ITaskRepository.findAll();
        Collection<Task> tasksByProjectID = new ArrayList<>();
        for (Task task : tasks) {
            if (projectID.equals(task.getProjectID())) {
                (tasksByProjectID).add(task);
            }
        }
        return tasksByProjectID;
    }

    @Override
    public Task remove(String id) {
        if (!StringValidator.validate(id)) return null;
        return ITaskRepository.remove(id);
    }

    @Override
    public Task edit(String id, String name, String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        return ITaskRepository.merge(task);
    }

    @Override
    public Task findOne(String id) {
        if (!StringValidator.validate(id)) return null;
        return ITaskRepository.findOne(id);
    }
}
