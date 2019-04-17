package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskService {

    void clear(String projectID);
    Task create(String name, String description, String projectID);
    Collection<Task> findAllByProjectID(String projectID);
    Task remove(String id);
    Task edit(String id, String name, String description);
    Task findOne(String id);
}
