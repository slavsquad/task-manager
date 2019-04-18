package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.IAbstractRepository;
import ru.stepanenko.tm.entity.AbstractEntity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

abstract public class AbstractRepository<E extends AbstractEntity> implements IAbstractRepository<E> {
    private final Map<String, E> repositoryMap = new HashMap<>();

    @Override
    public E findOne(final String id) {
        return repositoryMap.get(id);
    }

    @Override
    public Collection<E> findAll() {
        return repositoryMap.values();
    }

    @Override
    public void removeAll() {
        repositoryMap.clear();
    }

    @Override
    public E remove(final String id) {
        return repositoryMap.remove(id);
    }

    @Override
    public E persist(final E entity) {
        return merge(entity);
    }

    @Override
    public E merge(final E entity) {
        repositoryMap.put(entity.getId(), entity);
        return entity;
    }
}
