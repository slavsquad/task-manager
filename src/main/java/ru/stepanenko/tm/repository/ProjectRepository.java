package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ProjectRepository implements IProjectRepository {
    private Map<String, Project> projects = new HashMap<>();

    @Override
    public Project findOne(String id) {
        return projects.get(id);
    }

    @Override
    public Collection<Project> findAll() {
        return projects.values();
    }

    @Override
    public void removeAll() {
        projects.clear();
    }

    @Override
    public Project remove(String id) {
        return projects.remove(id);
    }

    @Override
    public Project persist(Project project) {
        return merge(project);
    }

    @Override
    public Project merge(Project project) {
        projects.put(project.getId(), project);
        return project;
    }
}
