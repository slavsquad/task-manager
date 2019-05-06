package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;

public interface IAbstractRepository<E> {

    @SneakyThrows
    E findOne(@NotNull final String id);

    @SneakyThrows
    Collection<E> findAll();

    @SneakyThrows
    void removeAll();

    @SneakyThrows
    E remove(@NotNull final String id);

    @SneakyThrows
    E persist(@NotNull final E entity);

    @SneakyThrows
    E merge(@NotNull final E entity);

    @SneakyThrows
    Collection<E> recovery(@NotNull final Collection<E> collection);

    Connection getConnection();
}
