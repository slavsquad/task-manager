package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskRepository extends IAbstractRepository<Task> {

    Collection<Task> findAllByUserId(final String id);
    Collection<Task> findAllByProjectId(final String id);
    void removeAllByUserId(final String id);
    void removeAllByProjectId(final String id);
}
