package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IAbstractRepository;
import ru.stepanenko.tm.api.service.IAbstractEntityService;
import ru.stepanenko.tm.entity.AbstractEntity;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;

@AllArgsConstructor
public abstract class AbstractEntityService<E extends AbstractEntity, R extends IAbstractRepository<E>> implements IAbstractEntityService<E> {

    @NotNull
    protected R repository;

    @Override
    public void clear() {
        repository.removeAll();
    }

    @Override
    public E findOne(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findOne(id);
    }

    @Override
    public E remove(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.remove(id);
    }

    @Override
    public Collection<E> recovery(Collection<E> collection) {
        return repository.recovery(collection);
    }

    @Override
    public Collection<E> findAll() {
        return repository.findAll();
    }
}
