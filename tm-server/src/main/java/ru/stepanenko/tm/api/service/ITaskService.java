package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.util.Collection;

public interface ITaskService{


    Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID);

    Task edit(@NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status);

    Task findOne(@NotNull final String id, @NotNull final String userId);

    Task remove(@NotNull final String id, @NotNull final String userId);

    void clear();

    Task findOne(@NotNull final String id);

    Task remove(@NotNull final String id);

    void recovery(@NotNull final Collection<Task> collection);

    Collection<Task> findAll();

    Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId);

    Collection<Task> findAllByUserId(@NotNull final String id);

    Integer removeAllByProjectId(@NotNull final String id, @NotNull final String userId);

    Integer removeAllByUserId(@NotNull final String id);

    Collection<Task> sortAllByUserId(@NotNull final String id, @NotNull final String comparator);

    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
