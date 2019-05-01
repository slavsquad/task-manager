package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskService extends IAbstractEntityService<Task> {

    Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID);
    Task edit(@NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status);
    Task findOne(@NotNull final String id, @NotNull final String userId);
    Task remove(@NotNull final String id, @NotNull final String userId);
    Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId);
    Collection<Task> findAllByUserId(@NotNull final String id);
    void removeAllByProjectId(@NotNull final String id, @NotNull final String userId);
    void removeAllByUserId(@NotNull final String id);
    Collection<Task> sortAllByUserId(@NotNull final String id, @NotNull final String comparator);
    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
