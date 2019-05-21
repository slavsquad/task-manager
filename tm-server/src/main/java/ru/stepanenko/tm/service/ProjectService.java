package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.DataValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProjectService implements IProjectService {

    @NotNull
    private final EntityManagerFactory entityManagerFactory;

    @Inject
    public ProjectService(@NotNull final EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void create(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException {
        DataValidator.validateProjectDTO(projectDTO);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Project project = convertDTOtoProject(projectDTO, entityManager);
            projectRepository
                    .persist(project);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void edit(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException {
        DataValidator.validateProjectDTO(projectDTO);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Project project = projectRepository.findOne(projectDTO.getId());
            if (project == null) throw new DataValidateException("Project not found!");
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setStatus(projectDTO.getStatus());
            project.setDateBegin(projectDTO.getDateBegin());
            project.setDateEnd(projectDTO.getDateEnd());
            projectRepository
                    .merge(project);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            e.printStackTrace();
            //throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public ProjectDTO findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Project project = projectRepository
                    .findOneByUserId(id, getUser(userId, entityManager));
            if (project == null) throw new DataValidateException("Project not found!");
            entityManager.getTransaction().commit();
            return project.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Project project = projectRepository
                    .findOneByUserId(id, getUser(userId, entityManager));
            if (project == null) throw new DataValidateException("Project not found!");
            projectRepository
                    .remove(project);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clear(
    ) throws DataValidateException {
        @Nullable final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @Nullable final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            projectRepository
                    .removeAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public ProjectDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Project project = projectRepository
                    .findOne(id);
            if (project == null) throw new DataValidateException("Project not found!");
            entityManager.getTransaction().commit();
            return project.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Project project = projectRepository
                    .findOne(id);
            if (project == null) throw new DataValidateException("Project not found!");
            projectRepository
                    .remove(project);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<ProjectDTO> findAll(
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<Project> projects = projectRepository
                    .findAll();
            if (projects == null) throw new DataValidateException("Projects not found!");
            entityManager.getTransaction().commit();
            return projects
                    .stream()
                    .map(Project::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<ProjectDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<Project> projects = projectRepository
                    .findAllByUserId(getUser(id, entityManager));
            if (projects == null) throw new DataValidateException("Projects not found!");
            entityManager.getTransaction().commit();
            return projects
                    .stream()
                    .map(Project::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            projectRepository
                    .removeAllByUserID(getUser(id, entityManager));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<ProjectDTO> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException {
        DataValidator.validateString(id, parameter);
        DataValidator.validateParameter(parameter);
        if ("order".equals(parameter)) return findAllByUserId(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<Project> projects = projectRepository
                    .sortAllByUserId(getUser(id, entityManager), parameter);
            if (projects == null) throw new DataValidateException("Projects not found!");
            entityManager.getTransaction().commit();
            return projects
                    .stream()
                    .map(Project::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException {
        DataValidator.validateString(name, description, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<Project> projects = projectRepository
                    .findAllByPartOfNameOrDescription(name, description, getUser(userId, entityManager));
            if (projects == null) throw new DataValidateException("Projects not found!");
            entityManager.getTransaction().commit();
            return projects
                    .stream()
                    .map(Project::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private Project convertDTOtoProject(
            @NotNull final ProjectDTO projectDTO,
            @NotNull final EntityManager entityManager
    ) throws DataValidateException {
        @NotNull final Project project = new Project();
        project.setId(projectDTO.getId());
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setDateBegin(projectDTO.getDateBegin());
        project.setDateEnd(projectDTO.getDateEnd());
        project.setUser(getUser(projectDTO.getUserId(), entityManager));
        project.setStatus(projectDTO.getStatus());
        return project;
    }

    private User getUser(
            @NotNull final String userId,
            @NotNull final EntityManager em
    ) throws DataValidateException {
        @NotNull final IUserRepository userRepository = new UserRepository(em);
        @Nullable final User user = userRepository.findOne(userId);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }
}
