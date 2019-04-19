package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskService extends IAbstractEntityService<Task> {

    Task create(final String name, final String description, final String projectID, final String userID);
    Task edit(final String id, final String name, final String description);
    Collection<Task> findAllByProjectID(final String id);
    Collection<Task> findAllByUserID(final String id);
    void removeAllByProjectId(final String id);
    void removeAllByUserId(final String id);
}
