package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class ProjectRepository implements IProjectRepository {
    final private Map<String, Project> projects = new HashMap<>();

    @Override
    public Project findOne(final String id) {
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
    public Project remove(final String id) {
        return projects.remove(id);
    }

    @Override
    public Project persist(final Project project) {
        return merge(project);
    }

    @Override
    public Project merge(final Project project) {
        projects.put(project.getId(), project);
        return project;
    }
}
