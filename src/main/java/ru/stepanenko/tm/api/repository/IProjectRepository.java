package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.Project;

import java.util.Collection;
import java.util.Comparator;

@Repository
public interface IProjectRepository {

    Project findOne(
            @NotNull final String id);

    Collection<Project> findAll();

    void removeAll();

    void remove(
            @NotNull final String id);

    void persist(
            @NotNull final Project project);

    void merge(
            @NotNull final Project project);

    Collection<Project> findAllByUserId(
            @NotNull final String id);

    Project findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId);

    void removeAllByUserId(
            @NotNull final String id);

    Collection<Project> sortAllByUserId(
            @NotNull final String id,
            @NotNull final Comparator<Project> comparator);

    Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId);
}
