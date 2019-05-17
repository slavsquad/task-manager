package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.exception.DataValidateException;

import java.util.Collection;

public interface ITaskService {

    Task create(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String projectID,
            @NotNull final String userID)
            throws DataValidateException;

    Task edit(
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String status)
            throws DataValidateException;

    Task findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    Task remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void clear()
            throws DataValidateException;

    Task findOne(
            @NotNull final String id)
            throws DataValidateException;

    Task remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<Task> findAll()
            throws DataValidateException;

    Collection<Task> findAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    Collection<Task> findAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    void removeAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void removeAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator)
            throws DataValidateException;

    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws DataValidateException;
}
