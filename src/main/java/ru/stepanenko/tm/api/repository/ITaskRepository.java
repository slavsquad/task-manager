package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskRepository extends IAbstractRepository<Task> {

    Collection<Task> findAllByUserId(@NotNull final String id);
    Collection<Task> findAllByProjectId(@NotNull final String id);
    void removeAllByUserId(@NotNull final String id);
    void removeAllByProjectId(@NotNull final String id);
}
