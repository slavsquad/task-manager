package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;

public final class ProjectService extends AbstractEntityService<Project, IProjectRepository> implements IProjectService {

    public ProjectService(@NotNull final IProjectRepository projectRepository) {
        super(projectRepository);
    }

    @Override
    public Project create(@NotNull final String name, @NotNull final String description, @NotNull final String userID) {
        if (!StringValidator.validate(name, description, userID)) return null;
        return repository.persist(new Project(name, description, userID));
    }

    @Override
    public Collection<Project> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByUserID(id);
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        repository.removeAllByUserID(id);
    }

    @Override
    public Project edit(@NotNull final String id, @NotNull final String name, @NotNull final String description) {
        if (!StringValidator.validate(id, name, description)) return null;
        @NotNull
        Project project = findOne(id);
        project.setName(name);
        project.setDescription(description);
        return repository.merge(project);
    }
}
