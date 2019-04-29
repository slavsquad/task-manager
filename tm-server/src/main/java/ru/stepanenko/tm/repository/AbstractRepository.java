package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IAbstractRepository;
import ru.stepanenko.tm.entity.AbstractEntity;

import java.util.*;

public abstract class AbstractRepository<E extends AbstractEntity> implements IAbstractRepository<E> {

    @NotNull
    private final Map<String, E> repositoryMap = new LinkedHashMap<>();

    @Override
    public E findOne(@NotNull final String id) {
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
    public E remove(@NotNull final String id) {
        return repositoryMap.remove(id);
    }

    @Override
    public E persist(@NotNull final E entity) {
        return merge(entity);
    }

    @Override
    public E merge(@NotNull final E entity) {
        repositoryMap.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Collection<E> recovery(@NotNull Collection<E> collection) {
        removeAll();
        for (E entity : collection) {
            persist(entity);
        }
        return collection;
    }
}
