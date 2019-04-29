package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;
import java.util.Comparator;

public interface ITaskRepository extends IAbstractRepository<Task> {

    Collection<Task> findAllByUserId(@NotNull final String id);
    Collection<Task> findAllByProjectId(@NotNull final String id);
    void removeAllByUserId(@NotNull final String id);
    void removeAllByProjectId(@NotNull final String id);
    Collection<Task> sortAllByUserId(@NotNull final String id, Comparator<Task> comparator);
    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
