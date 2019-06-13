package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.util.ComparatorUtil;
import ru.stepanenko.tm.util.DataValidator;

import java.util.Collection;

@Service
public class ProjectService implements IProjectService {

    @NotNull
    private final IProjectRepository projectRepository;

    @Autowired
    public ProjectService(@NotNull final IProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public void create(
            @Nullable final Project project
    ) throws DataValidateException {
        DataValidator.validateProject(project);
        projectRepository
                .persist(project);
    }

    @Override
    public void edit(
            @Nullable final Project project
    ) throws DataValidateException {
        DataValidator.validateProject(project);
        projectRepository
                .merge(project);
    }

    @Override
    public Project findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Project project = projectRepository
                .findOneByUserId(id, userId);
        if (project == null) throw new DataValidateException("Project not found!");
        return project;
    }

    @Override
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Project project = projectRepository
                .findOneByUserId(id, userId);
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository.remove(project.getId());
    }

    @Override
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<Project> projects = projectRepository.findAll();
        if (projects == null) throw new DataValidateException("Projects not found!");
        projectRepository.removeAll();
    }

    @Override
    public Project findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        return project;
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Project project = projectRepository
                .findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository
                .remove(project.getId());
    }

    @Override
    public Collection<Project> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<Project> projects = projectRepository
                .findAll();
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects;
    }

    @Override
    public Collection<Project> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByUserId(id);
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects;
    }

    @Override
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        projectRepository
                .removeAllByUserId(id);
    }

    @Override
    public Collection<Project> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException {
        DataValidator.validateString(id, parameter);
        DataValidator.validateParameter(parameter);
        @Nullable Collection<Project> projects = projectRepository.sortAllByUserId(id, ComparatorUtil.getComparator(parameter));
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects;
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(name, description, userId);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByPartOfNameOrDescription(name, description, userId);
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects;
    }

}
