package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

public interface ITaskRepository {

    Task findOne(@NotNull final String id);

    Collection<Task> findAll();

    void removeAll();

    void remove(
            @NotNull final Task task);

    void persist(
            @NotNull final Task entity);

    Task merge(
            @NotNull final Task task);



    Collection<Task> findAllByUserId(
            @NotNull final User user);

    Collection<Task> findAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user);


    Task findOneByUserId(
            @NotNull final String id,
            @NotNull final User user);

    void removeAllByUserId(
            @NotNull final User user);

    void removeAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user);


    Collection<Task> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter);

    Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user);
}
