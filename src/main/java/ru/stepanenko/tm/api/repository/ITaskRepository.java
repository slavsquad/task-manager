package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;

import java.util.Collection;
import java.util.Comparator;

@Repository
public interface ITaskRepository {

    Task findOne(@NotNull final String id);

    Collection<Task> findAll();

    void removeAll();

    void remove(
            @NotNull final String id);

    void persist(
            @NotNull final Task task);

    void merge(
            @NotNull final Task task);

    Collection<Task> findAllByUserId(
            @NotNull final String id);

    Collection<Task> findAllByProjectAndUserId(
            @NotNull final String projectId,
            @NotNull final String userId);


    Task findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId);

    void removeAllByUserId(
            @NotNull final String id);

    void removeAllByProjectAndUserId(
            @NotNull final String projectId,
            @NotNull final String userId);


    Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final Comparator<Task> comparator);

    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId);
}
