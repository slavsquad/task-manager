package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import java.util.Collection;

public interface ISessionRepository {

    Session findOne(
            @NotNull final String id);

    Collection<Session> findAll();

    void removeAll();

    void remove(
            @NotNull final Session session);

    void persist(
            @NotNull final Session session);

    Session merge(
            @NotNull final Session session);

    Collection<Session> findAllByUserId(
            @NotNull final User user);
}
