package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import java.util.Collection;

public interface ISessionRepository extends IAbstractRepository<Session> {
    @SneakyThrows
    Collection<Session> findAllByUserId(@NotNull final String id);
}
