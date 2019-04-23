package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.enumerate.Status;

import java.util.Collection;
import java.util.Date;

public interface IProjectService extends IAbstractEntityService<Project> {

    Project create(@NotNull final String name, @NotNull final String description, @NotNull final String userID);
    Project edit(@NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status);
    Collection<Project> findAllByUserId(@NotNull final String id);
    void removeAllByUserId(@NotNull final String id);
    Collection<Project> sortAllByUserId(@NotNull final String id, @NotNull final String comparator);
    Collection<Project> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
