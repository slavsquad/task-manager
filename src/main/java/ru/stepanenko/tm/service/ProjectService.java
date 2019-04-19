package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;

public final class ProjectService extends AbstractEntityService<Project> implements IProjectService {

    public ProjectService(final IProjectRepository projectRepository) {
        super(projectRepository);
    }

    @Override
    public Project create(final String name, final String description, final String userID) {
        if (!StringValidator.validate(name, description, userID)) return null;
        return repository.persist(new Project(name, description, userID));
    }

    @Override
    public Collection<Project> findAllByUserId(final String id) {
        if (!StringValidator.validate(id)) return null;
        return ((IProjectRepository) repository).findAllByUserID(id);
    }

    @Override
    public void removeAllByUserId(String id) {
        if (!StringValidator.validate(id)) return;
        ((IProjectRepository) repository).removeAllByUserID(id);
    }

    @Override
    public Project edit(final String id, final String name, final String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        Project project = findOne(id);
        project.setName(name);
        project.setDescription(description);
        return repository.merge(project);
    }
}
