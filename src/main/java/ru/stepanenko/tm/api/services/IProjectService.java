package ru.stepanenko.tm.api.services;

import ru.stepanenko.tm.entity.Project;
import java.util.Collection;

public interface IProjectService {

    void clear();
    boolean create(String name, String description);
    Collection<Project> findAll();
    Project findOne(String id);
    Project remove(String id);
    Project edit(String projectID, String newName, String newDescription);
}
