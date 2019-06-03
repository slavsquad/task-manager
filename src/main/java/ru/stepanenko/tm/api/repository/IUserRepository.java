package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

public interface IUserRepository {

    User findOne(
            @NotNull final String id);

    Collection<User> findAll();

    void removeAll();

    void remove(
            @NotNull final String id);

    void persist(
            @NotNull final User user);

    void merge(
            @NotNull final User user);

    User findByLogin(
            @NotNull final String login);
}
