package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;
import java.util.Collection;
import java.util.UUID;

public class ProjectService implements IProjectService {

    private IProjectRepository IProjectRepository;

    public ProjectService(IProjectRepository IProjectRepository) {
        this.IProjectRepository = IProjectRepository;
    }

    @Override
    public void clear() {
        IProjectRepository.removeAll();
    }

    @Override
    public Project create(String name, String description) {
        if (!StringValidator.validate(name, description)) {
            return null;
        }
        return IProjectRepository.persist(new Project(UUID.randomUUID().toString(), name, description));
    }

    @Override
    public Collection<Project> findAll() {
        return IProjectRepository.findAll();
    }

    @Override
    public Project findOne(String id) {
        if (!StringValidator.validate(id)) {
            return null;
        }
        return IProjectRepository.findOne(id);
    }

    @Override
    public Project remove(String id) {
        if (!StringValidator.validate(id)) {
            return null;
        }
        return IProjectRepository.remove(id);
    }

    @Override
    public Project edit(String id, String name, String description) {
        if (!StringValidator.validate(id, name, description)) {
            return null;
        }
        Project project = findOne(id);
        project.setName(name);
        project.setDescription(description);
        return IProjectRepository.merge(project);
    }
}
