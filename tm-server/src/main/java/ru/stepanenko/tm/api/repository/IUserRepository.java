package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.User;
import java.sql.Connection;
import java.util.Collection;

public interface IUserRepository {

    User findOne(
            @NotNull final String id);

    Collection<User> findAll();

    Integer removeAll();

    Integer remove(
            @NotNull final String id);

    Integer persist(
            @NotNull final User user);

    Integer merge(
            @NotNull final User user);

    Connection getConnection();

    User findByLogin(
            @NotNull final String login);
}
