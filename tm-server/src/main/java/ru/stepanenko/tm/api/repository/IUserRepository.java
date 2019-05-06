package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.User;

public interface IUserRepository extends IAbstractRepository<User> {
    @SneakyThrows
    User findByLogin(@NotNull final String login);
}
