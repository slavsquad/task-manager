package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Task;
import java.util.Collection;

public interface ITaskService {

    void clear(final String projectID);
    Task create(final String name, final String description, final String projectID, final String userID);
    Collection<Task> findAllByProjectID(final String projectID);
    Task remove(final String id);
    Task edit(final String id, final String name, final String description);
    Task findOne(final String id);
}
