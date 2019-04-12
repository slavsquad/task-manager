package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class TaskRepository implements ITaskRepository {

    private Map<String, Task> tasks = new HashMap<>();
    private static int idCount = 0;

    @Override
    public Task findOne(String id) {
        return tasks.get(id);
    }


    @Override
    public Collection<Task> findAll() {
        return tasks.values();
    }

    @Override
    public boolean removeAll() {
        tasks.clear();
        if (tasks.size() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Task remove(String id) {
        return tasks.remove(id);
    }

    @Override
    public void persist(String name, String description, String ProjectID) {
        Task task = new Task(UUID.randomUUID().toString(), name, description, ProjectID);
        task.setStartDate(LocalDateTime.now());
        tasks.put(task.getId(),task);
    }

    @Override
    public Task merge(String id, String newName, String newDescription)
    {
        Task oldTask = findOne(id);
        Task newTask = new Task(id,newName, newDescription, oldTask.getProjectID());
        newTask.setStartDate(oldTask.getStartDate());
        newTask.setEndDate(oldTask.getEndDate());
        return tasks.put(id, newTask);
    }
}
