package ru.stepanenko.tm.api;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskService extends IAbstractEntityService<Task> {

    Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID);
    Task edit(@NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status);
    Collection<Task> findAllByProjectId(@NotNull final String id);
    Collection<Task> findAllByUserId(@NotNull final String id);
    void removeAllByProjectId(@NotNull final String id);
    void removeAllByUserId(@NotNull final String id);
    Collection<Task> sortAllByUserId(@NotNull final String id, @NotNull final String comparator);
    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
