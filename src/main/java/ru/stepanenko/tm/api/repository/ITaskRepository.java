package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskRepository {

    Task findOne(String id);
    Collection<Task> findAll();
    boolean removeAll();
    Task remove(String id);
    void persist(String name, String description, String projectID);
    Task merge(String id, String newName, String newDescription);
}
