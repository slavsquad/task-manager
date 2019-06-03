package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Task;

import java.util.Collection;

public interface ITaskService {
    void create(
            @Nullable final Task task
    ) throws DataValidateException;

    void edit(
            @Nullable final Task taskDTO
    ) throws DataValidateException;

    Task findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void clear(
    ) throws DataValidateException;

    Task findOne(
            @Nullable final String id
    ) throws DataValidateException;

    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<Task> findAll(
    ) throws DataValidateException;

    Collection<Task> findAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    Collection<Task> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator
    ) throws DataValidateException;

    Collection<Task> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;
}
