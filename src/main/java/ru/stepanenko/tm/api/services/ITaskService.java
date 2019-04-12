package ru.stepanenko.tm.api.services;

import ru.stepanenko.tm.entity.Task;
import java.util.Collection;

public interface ITaskService {

    void clear(String projectID);
    boolean create(String name, String description, String projectID);
    Collection<Task> findAllByProjectID(String projectID);
    Task remove(String id);
    Task edit(String id, String newName, String newDescription);
    Task findOne(String id);
}
