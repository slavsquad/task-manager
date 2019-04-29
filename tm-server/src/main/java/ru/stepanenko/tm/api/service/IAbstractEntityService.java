package ru.stepanenko.tm.api.service;


import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface IAbstractEntityService<E> {

    void clear();
    E findOne(@NotNull final String id);
    E remove(@NotNull final String id);
    Collection<E> recovery(@NotNull final Collection<E> collection);
    Collection<E> findAll();
}
