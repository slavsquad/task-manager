package ru.stepanenko.tm.services.project;

import ru.stepanenko.tm.entity.Project;

import java.util.Collection;

public interface ProjectCommands {
    boolean clear();
    boolean create(String name,String description);
    Collection<Project> findAll();
    Project findOne(String id);
    Project remove(String id);
    Project edit(String projectID, String name, String description);
}
