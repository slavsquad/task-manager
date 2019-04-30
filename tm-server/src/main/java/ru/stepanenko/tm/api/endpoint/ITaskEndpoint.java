package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

import java.util.Collection;

public interface ITaskEndpoint {

    Task createTask(@NotNull final Session session, @NotNull final String name, @NotNull final String description, @NotNull final String projectID) throws InvalidSessionException;
    Task editTask(@NotNull final Session session, @NotNull final String id, @NotNull final String name, @NotNull final String description, @NotNull final String status) throws InvalidSessionException;
    Collection<Task> findAllTaskByProjectId(@NotNull final Session session, @NotNull final String id) throws InvalidSessionException;
    Collection<Task> findAllTaskByUserId(@NotNull final Session session) throws InvalidSessionException;
    void removeAllTaskByProjectId(@NotNull final Session session, @NotNull final String id) throws InvalidSessionException;
    void removeAllTaskByUserId(@NotNull final Session session) throws InvalidSessionException;
    Collection<Task> sortAllTaskByUserId(@NotNull final Session session, @NotNull final String comparator) throws InvalidSessionException;
    Collection<Task> findAllTaskByPartOfNameOrDescription(@NotNull final Session session, @NotNull final String name, @NotNull final String description) throws InvalidSessionException;
}
