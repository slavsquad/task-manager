package ru.stepanenko.tm.api.service;


import java.util.Collection;

public interface IAbstractEntityService<E> {

    void clear();
    E findOne(final String id);
    E remove(final String id);
    Collection<E> findAll();
}
