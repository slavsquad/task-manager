package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Project;
import java.util.Collection;

public interface IProjectRepository extends IAbstractRepository<Project>{
    Collection<Project> findAllByUserID(final String id);
}
