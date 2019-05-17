package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

public interface IProjectRepository {

    Project findOne(
            @NotNull final String id);

    Collection<Project> findAll();

    void removeAll();

    void remove(
            @NotNull final Project project);

    void persist(
            @NotNull final Project project);

    Project merge(
            @NotNull final Project project);

    Collection<Project> findAllByUserId(
            @NotNull final User user);

    Project findOneByUserId(
            @NotNull final String id,
            @NotNull final User user);

    void removeAllByUserID(
            @NotNull final User user);

    Collection<Project> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter);

    Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user);
}
