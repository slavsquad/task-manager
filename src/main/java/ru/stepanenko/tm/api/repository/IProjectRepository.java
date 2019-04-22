package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;

import java.util.Collection;

public interface IProjectRepository extends IAbstractRepository<Project> {

    Collection<Project> findAllByUserID(@NotNull final String id);
    void removeAllByUserID(@NotNull final String id);
}
