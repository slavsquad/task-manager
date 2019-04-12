package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.Project;
import java.util.Collection;

public interface IProjectRepository {

    Project findOne(String id);
    Collection<Project> findAll();
    void removeAll();
    Project remove(String id);
    void persist(String name, String description);
    Project merge(String id, String newName, String newDescription);
}
