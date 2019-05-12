package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;
import java.util.Date;

public final class ProjectService implements IProjectService {

    @NotNull final IProjectRepository repository;
    public ProjectService(@NotNull final IProjectRepository projectRepository) {
        this.repository = projectRepository;
    }

    @Override
    public Project create(@NotNull final String name, @NotNull final String description, @NotNull final String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        @NotNull Project project = new Project(name, description, userId);
        repository.persist(project);
        return findOne(project.getId());
    }

    @Override
    public Project edit(@NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status, @NotNull String userId) {
        if (!StringValidator.validate(id, name, description, status)) return null;
        if (EnumUtil.stringToStatus(status) == null) return null;
        @Nullable final Project project = findOne(id, userId);
        if (project==null) return null;
        project.setName(name);
        project.setDescription(description);
        project.setStatus(EnumUtil.stringToStatus(status));
        if (Status.DONE == EnumUtil.stringToStatus(status)) {
            project.setDateEnd(new Date());
        } else {
            project.setDateEnd(null);
        }
        repository.merge(project);
        return findOne(project.getId());
    }

    @Override
    public Project findOne(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        return repository.findOne(id, userId);
    }

    @Override
    public Project remove(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        @Nullable final Project project = findOne(id, userId);
        if(project==null) return null;
        repository.remove(id, userId);
        return project;
    }

    @Override
    public void clear() {
        repository.removeAll();
    }

    @Override
    public Project findOne(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findOne(id);
    }

    @Override
    public Project remove(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        @Nullable final Project project = findOne(id);
        if(project==null) return null;
        repository.remove(id);
        return project;
    }

    @Override
    public void recovery(@NotNull Collection<Project> collection) {
        repository.recovery(collection);
    }

    @Override
    public Collection<Project> findAll() {
        return repository.findAll();
    }

    @Override
    public Collection<Project> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        return repository.findAllByUserId(id);
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        repository.removeAllByUserID(id);
    }

    @Override
    public Collection<Project> sortAllByUserId(@NotNull String id, @NotNull String parameter) {
        if (!StringValidator.validate(id, parameter)) return null;
        if (!ParameterValidator.validate(parameter)) return null;
        if ("order".equals(parameter)) return findAllByUserId(id);
        return repository.sortAllByUserId(id, parameter);
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        return repository.findAllByPartOfNameOrDescription(name, description, userId);
    }
}
