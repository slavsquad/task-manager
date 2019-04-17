package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;

import java.util.ArrayList;
import java.util.Collection;

public class ProjectService implements IProjectService {
    private IProjectRepository projectRepository;

    public ProjectService(IProjectRepository IProjectRepository) {
        this.projectRepository = IProjectRepository;
    }

    @Override
    public void clear() {
        projectRepository.removeAll();
    }

    @Override
    public Project create(String name, String description, String userID) {
        if (!StringValidator.validate(name, description, userID)) return null;
        return projectRepository.persist(new Project(name, description, userID));
    }

    @Override
    public Collection<Project> findAll(String userID) {
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
    public Project findOne(String id) {
        if (!StringValidator.validate(id)) return null;
        return projectRepository.findOne(id);
    }

    @Override
    public Project remove(String id) {
        if (!StringValidator.validate(id)) return null;
        return projectRepository.remove(id);
    }

    @Override
    public Project edit(String id, String name, String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        Project project = findOne(id);
        project.setName(name);
        project.setDescription(description);
        return projectRepository.merge(project);
    }
}
