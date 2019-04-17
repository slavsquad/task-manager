package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskRepository {

    Task findOne(String id);
    Collection<Task> findAll();
    void removeAll();
    Task remove(String id);
    Task persist(Task task);
    Task merge(Task task);
}
