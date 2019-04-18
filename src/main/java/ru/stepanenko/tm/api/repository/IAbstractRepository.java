package ru.stepanenko.tm.api.repository;

import java.util.Collection;

public interface IAbstractRepository<E> {
    E findOne(final String id);
    Collection<E> findAll();
    void removeAll();
    E remove(final String id);
    E persist(final E entity);
    E merge(final E entity);
}
