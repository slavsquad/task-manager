package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;

import java.util.ArrayList;
import java.util.Collection;

public final class ProjectService implements IProjectService {
    private final IProjectRepository projectRepository;

    public ProjectService(final IProjectRepository IProjectRepository) {
        this.projectRepository = IProjectRepository;
    }

    @Override
    public void clear() {
        projectRepository.removeAll();
    }

    @Override
    public Project create(final String name, final String description, final String userID) {
        if (!StringValidator.validate(name, description, userID)) return null;
        return projectRepository.persist(new Project(name, description, userID));
    }

    @Override
    public Collection<Project> findAll(final String userID) {
        if (!StringValidator.validate(userID)) return null;
        Collection<Project> userProjects = new ArrayList<>();
        for (Project project : projectRepository.findAll()) {
            if (userID.equals(project.getUserID())) {
                userProjects.add(project);
            }
        }
        return userProjects;
    }

    @Override
    public Project findOne(final String id) {
        if (!StringValidator.validate(id)) return null;
        return projectRepository.findOne(id);
    }

    @Override
    public Project remove(final String id) {
        if (!StringValidator.validate(id)) return null;
        return projectRepository.remove(id);
    }

    @Override
    public Project edit(final String id, final String name, final String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        Project project = findOne(id);
        project.setName(name);
        project.setDescription(description);
        return projectRepository.merge(project);
    }
}
