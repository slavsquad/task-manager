package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface IAbstractRepository<E> {

    E findOne(@NotNull final String id);
    Collection<E> findAll();
    void removeAll();
    E remove(@NotNull final String id);
    E persist(@NotNull final E entity);
    E merge(@NotNull final E entity);
}
