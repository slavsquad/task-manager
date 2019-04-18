package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class TaskRepository implements ITaskRepository {
    final private Map<String, Task> tasks = new HashMap<>();

    @Override
    public Task findOne(final String id) {
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
    public Task remove(final String id) {
        return tasks.remove(id);
    }

    @Override
    public Task persist(final Task task) {
        return merge(task);
    }

    @Override
    public Task merge(final Task task) {
        tasks.put(task.getId(), task);
        return task;
    }
}
