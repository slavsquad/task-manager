package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.util.DataValidator;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProjectService implements IProjectService {

    @NotNull
    final IProjectRepository projectRepository;

    @NotNull
    final IUserRepository userRepository;

    @Autowired
    public ProjectService(
            @NotNull final IProjectRepository projectRepository,
            @NotNull final IUserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void create(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException {
        DataValidator.validateProjectDTO(projectDTO);
        try {
            @NotNull final Project project = convertDTOtoProject(projectDTO);
            projectRepository
                    .save(project);
        } catch (Exception e) {
            throw new DataValidateException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void edit(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException {
        DataValidator.validateProjectDTO(projectDTO);
        @Nullable final Project project = projectRepository
                .findById(projectDTO.getId()).get();
        if (project == null) throw new DataValidateException("Project not found!");
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStatus(projectDTO.getStatus());
        project.setDateBegin(projectDTO.getDateBegin());
        project.setDateEnd(projectDTO.getDateEnd());
        projectRepository
                .save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Project project = projectRepository
                .findByIdAndUser(id, getUser(userId));
        if (project == null) throw new DataValidateException("Project not found!");
        return project.getDTO();
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @Nullable final Project project = projectRepository
                .findByIdAndUser(id, getUser(userId));
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository
                .delete(project);
    }

    @Override
    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<Project> projects = projectRepository.findAll();
        if (projects == null) throw new DataValidateException("Projects not found!");
        projects.forEach(projectRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @Nullable final Project project = projectRepository
                .findById(id).get();
        if (project == null) throw new DataValidateException("Project not found!");
        return project.getDTO();
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Project project = projectRepository
                .findById(id).get();
        if (project == null) throw new DataValidateException("Project not found!");
        projectRepository
                .delete(project);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProjectDTO> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<Project> projects = projectRepository
                .findAll();
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects
                .stream()
                .map(Project::getDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProjectDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByUser(getUser(id));
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects
                .stream()
                .map(Project::getDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final User user = getUser(id);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByUser(user);
        if (projects == null) throw new DataValidateException("Projects not found!");
        projects.forEach(projectRepository::delete);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProjectDTO> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException {
        DataValidator.validateString(id, parameter);
        DataValidator.validateParameter(parameter);
        @Nullable Collection<Project> projects = null;
        switch (parameter) {
            case "order":
                projects = projectRepository
                        .findAllByUser(getUser(id));
                break;
            case "status":
                projects = projectRepository
                        .sortByStatus(getUser(id));
                break;
            case "dateBegin":
                projects = projectRepository
                        .sortByDateBegin(getUser(id));
                break;
            case "dateEnd":
                projects = projectRepository
                        .sortByDateEnd(getUser(id));
                break;
        }
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects
                .stream()
                .map(Project::getDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(name, description, userId);
        @Nullable final Collection<Project> projects = projectRepository
                .findAllByPartOfNameOrDescription(name, description, getUser(userId));
        if (projects == null) throw new DataValidateException("Projects not found!");
        return projects
                .stream()
                .map(Project::getDTO)
                .collect(Collectors.toList());
    }

    private Project convertDTOtoProject(
            @NotNull final ProjectDTO projectDTO
    ) throws DataValidateException {
        @NotNull final Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setDateBegin(projectDTO.getDateBegin());
        project.setDateEnd(projectDTO.getDateEnd());
        project.setUser(getUser(projectDTO.getUserId()));
        project.setStatus(projectDTO.getStatus());
        return project;
    }

    @Transactional(readOnly = true)
    public User getUser(
            @NotNull final String userId
    ) throws DataValidateException {
        @Nullable final User user = userRepository.findById(userId).get();
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }
}