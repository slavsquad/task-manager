package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import java.util.Collection;

public interface IProjectEndpoint {

    Project createProject(@NotNull final Session session, @NotNull final String name, @NotNull final String description) throws InvalidSessionException;
    Project editProject(@NotNull final Session session, @NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status) throws InvalidSessionException;
    Collection<Project> findAllProjectByUserId(@NotNull final Session session) throws InvalidSessionException;
    void removeAllProjectByUserId(@NotNull final Session session) throws InvalidSessionException;
    Collection<Project> sortAllProjectByUserId(@NotNull final Session session, @NotNull final String comparator) throws InvalidSessionException;
    Collection<Project> findAllProjectByPartOfNameOrDescription(@NotNull final Session session, @NotNull final String name, @NotNull final String description) throws InvalidSessionException;
}
