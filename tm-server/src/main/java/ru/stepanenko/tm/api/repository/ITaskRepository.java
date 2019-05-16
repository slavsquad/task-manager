package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Task;
import java.sql.Connection;
import java.util.Collection;

public interface ITaskRepository {

    Task findOne(@NotNull final String id);

    Collection<Task> findAll();

    Integer removeAll();

    Integer remove(
            @NotNull final String id);

    Integer persist(
            @NotNull final Task entity);

    Integer merge(
            @NotNull final Task task);

    Connection getConnection();

    Collection<Task> findAllByUserId(
            @NotNull final String id);

    Collection<Task> findAllByProjectAndUserId(
            @NotNull final String id,
            @NotNull final String userId);


    Task findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId);

    Integer removeOneByUserId(
            @NotNull final String id,
            @NotNull final String userId);

    Integer removeAllByUserId(
            @NotNull final String id);

    Integer removeAllByProjectAndUserId(
            @NotNull final String id,
            @NotNull final String userId);


    Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter);

    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId);
}
