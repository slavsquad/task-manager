package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IAbstractRepository;
import ru.stepanenko.tm.api.service.IAbstractEntityService;
import ru.stepanenko.tm.entity.AbstractEntity;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;

public abstract class AbstractEntityService<E extends AbstractEntity> implements IAbstractEntityService<E> {
    protected IAbstractRepository<E> repository;

    public AbstractEntityService(IAbstractRepository repository) {
        this.repository = repository;
    }

    @Override
    public void clear() {
        repository.removeAll();
    }

    @Override
    public E findOne(String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findOne(id);
    }

    @Override
    public E remove(String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.remove(id);
    }

    @Override
    public Collection<E> findAll() {
        return repository.findAll();
    }
}
