package ru.stepanenko.tm.repository;

import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
    public void persist(String name, String description) {
        Project project = new Project(UUID.randomUUID().toString(), name, description);
        project.setStartDate(LocalDateTime.now());
        projects.put(project.getId(), project);
    }

    @Override
    public Project merge(String id, String newName, String newDescription) {
        Project oldProject = findOne(id);
        Project newProject = new Project(id,newName,newDescription);
        newProject.setStartDate(oldProject.getStartDate());
        newProject.setEndDate(oldProject.getEndDate());
        return projects.put(id, newProject);
    }

    public Map<String, Project> getProjects() {
        return projects;
    }
}
