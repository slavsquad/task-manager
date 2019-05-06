package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;
import java.util.Collection;
import java.util.Comparator;

public interface ITaskRepository extends IAbstractRepository<Task> {

    @SneakyThrows
    Collection<Task> findAllByUserId(@NotNull final String id);

    @SneakyThrows
    Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    Task findOne(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    Task remove(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    void removeAllByUserId(@NotNull final String id);

    @SneakyThrows
    void removeAllByProjectId(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    Collection<Task> sortAllByUserId(@NotNull final String id, Comparator<Task> comparator);

    @SneakyThrows
    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
