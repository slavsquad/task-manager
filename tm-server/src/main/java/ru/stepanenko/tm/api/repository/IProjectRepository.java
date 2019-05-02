package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;

import java.util.Collection;
import java.util.Comparator;

public interface IProjectRepository extends IAbstractRepository<Project> {

    Collection<Project> findAllByUserId(@NotNull final String id);

    Project findOne(@NotNull final String id, @NotNull final String userId);

    Project remove(@NotNull final String id, @NotNull final String userId);

    void removeAllByUserID(@NotNull final String id);

    Collection<Project> sortAllByUserId(@NotNull final String id, Comparator<Project> comparator);

    Collection<Project> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
