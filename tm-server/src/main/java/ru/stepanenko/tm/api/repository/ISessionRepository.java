package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Session;
import java.sql.Connection;
import java.util.Collection;

public interface ISessionRepository {

    Session findOne(
            @NotNull final String id);

    Collection<Session> findAll();

    Integer removeAll();

    Integer remove(
            @NotNull final String id);

    Integer persist(
            @NotNull final Session session);

    Integer merge(
            @NotNull final Session session);

    Connection getConnection();

    Collection<Session> findAllByUserId(
            @NotNull final String id);
}
