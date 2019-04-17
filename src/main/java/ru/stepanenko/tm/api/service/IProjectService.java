package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.Project;

import java.util.Collection;

public interface IProjectService {

    void clear();
    Project create(String name, String description, String userID);
    Collection<Project> findAll(String userID);
    Project findOne(String id);
    Project remove(String id);
    Project edit(String id, String name, String description);
}
