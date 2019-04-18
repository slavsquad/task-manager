package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Project;
import java.util.Collection;

public interface IProjectService {

    void clear();
    Project create(final String name, final String description, final String userID);
    Collection<Project> findAll();
    Collection<Project> findAllByUserId(String id);
    Project findOne(final String id);
    Project remove(final String id);
    Project edit(final String id, final String name, final String description);
}
