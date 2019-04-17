package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TaskRepository implements ITaskRepository {
    private Map<String, Task> tasks = new HashMap<>();

    @Override
    public Task findOne(String id) {
        return tasks.get(id);
    }

    @Override
    public Collection<Task> findAll() {
        return tasks.values();
    }

    @Override
    public void removeAll() {
        tasks.clear();
    }

    @Override
    public Task remove(String id) {
        return tasks.remove(id);
    }

    @Override
    public Task persist(Task task) {
        return merge(task);
    }

    @Override
    public Task merge(Task task) {
        tasks.put(task.getId(), task);
        return task;
    }
}
