package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Project;
import java.util.Collection;

public interface IProjectRepository {

    Project findOne(final String id);
    Collection<Project> findAll();
    void removeAll();
    Project remove(final String id);
    Project persist(final Project project);
    Project merge(final Project project);
}
