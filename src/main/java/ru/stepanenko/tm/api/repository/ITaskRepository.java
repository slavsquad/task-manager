package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Task;
import java.util.Collection;

public interface ITaskRepository {

    Task findOne(final String id);
    Collection<Task> findAll();
    void removeAll();
    Task remove(final String id);
    Task persist(final Task task);
    Task merge(final Task task);
}
