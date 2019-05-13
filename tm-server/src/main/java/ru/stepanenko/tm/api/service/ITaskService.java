package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.InputDataValidateException;

import java.util.Collection;

public interface ITaskService {

    Task create(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String projectID,
            @NotNull final String userID)
            throws InputDataValidateException;

    Task edit(
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String status)
            throws InputDataValidateException;

    Task findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    Task remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    void clear()
            throws InputDataValidateException;

    Task findOne(
            @NotNull final String id)
            throws InputDataValidateException;

    Task remove(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<Task> findAll()
            throws InputDataValidateException;

    Collection<Task> findAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    Collection<Task> findAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException;

    void removeAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    void removeAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator)
            throws InputDataValidateException;

    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws InputDataValidateException;
}
