package ru.stepanenko.tm.services;

import ru.stepanenko.tm.api.services.ITaskService;
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
        if (removalTasks.size() != 0) {
            for (Task task : removalTasks) {
                ITaskRepository.remove(task.getId());
            }
        }
    }

    @Override
    public boolean create(String name, String description, String projectID) {

        if (StringValidator.validate(name, description, projectID)) {
            ITaskRepository.persist(name, description, projectID);
            return true;
        }
        return false;
    }

    @Override
    public Collection<Task> findAllByProjectID(String projectID) {
        Collection<Task> tasks = ITaskRepository.findAll();
        Collection<Task> tasksByProjectID = new ArrayList<>();

        if (StringValidator.validate(projectID)){
            for (Task task:tasks) {
                if (projectID.equals(task.getProjectID())) {
                    (tasksByProjectID).add(task);
                }
            }
        }
        return tasksByProjectID;
    }

    @Override
    public Task remove(String id) {
        if (StringValidator.validate(id)) {
            return ITaskRepository.remove(id);
        }
        return null;
    }

    @Override
    public Task edit(String id, String newName, String newDescription) {

        if (StringValidator.validate(id,newName,newDescription)){
            return ITaskRepository.merge(id, newName, newDescription);
        }
        return null;
    }

    @Override
    public Task findOne(String id) {
        if (StringValidator.validate(id)) {
            return ITaskRepository.findOne(id);
        }
        return null;
    }
}
