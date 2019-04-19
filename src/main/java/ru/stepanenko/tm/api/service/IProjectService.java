package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Project;

import java.util.Collection;

public interface IProjectService extends IAbstractEntityService<Project> {

    Project create(final String name, final String description, final String userID);
    Project edit(final String id, final String name, final String description);
    Collection<Project> findAllByUserId(String id);
    void removeAllByUserId(String id);
}
