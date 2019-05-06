package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import java.util.Collection;
import java.util.Comparator;

public interface IProjectRepository extends IAbstractRepository<Project> {

    @SneakyThrows
    Collection<Project> findAllByUserId(@NotNull final String id);

    @SneakyThrows
    Project findOne(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    Project remove(@NotNull final String id, @NotNull final String userId);

    @SneakyThrows
    void removeAllByUserID(@NotNull final String id);

    @SneakyThrows
    Collection<Project> sortAllByUserId(@NotNull final String id, Comparator<Project> comparator);

    @SneakyThrows
    Collection<Project> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);
}
