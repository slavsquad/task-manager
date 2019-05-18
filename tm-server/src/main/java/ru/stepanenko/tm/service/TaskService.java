package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.ProjectRepository;
import ru.stepanenko.tm.repository.TaskRepository;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.DataValidator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public final class TaskService implements ITaskService {

    @NotNull
    final EntityManagerFactory entityManagerFactory;

    @Override
    public void create(
            @NotNull final TaskDTO taskDTO)
            throws DataValidateException {
        DataValidator.validateTaskDTO(taskDTO);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = convertDTOtoTask(taskDTO, entityManager);
            taskRepository.persist(task);
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
            @NotNull final TaskDTO taskDTO)
            throws DataValidateException {
        DataValidator.validateTaskDTO(taskDTO);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = convertDTOtoTask(taskDTO, entityManager);
            taskRepository.merge(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public TaskDTO findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = taskRepository
                    .findOneByUserId(id, getUser(userId, entityManager));
            entityManager.getTransaction().commit();
            if (task==null) throw new DataValidateException("Task not found");
            return task.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = taskRepository
                    .findOneByUserId(id, getUser(userId, entityManager));
            if (task==null) throw new DataValidateException("Task not found");
            taskRepository.remove(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void clear()
            throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            taskRepository.removeAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public TaskDTO findOne(
            @NotNull final String id)
            throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = taskRepository
                    .findOne(id);
            entityManager.getTransaction().commit();
            if (task==null) throw new DataValidateException("Task not found");
            return task.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @NotNull final String id)
            throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Task task = taskRepository
                    .findOne(id);
            if (task==null) throw new DataValidateException("Task not found");
            taskRepository.remove(task);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<TaskDTO> findAll()
            throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Collection<Task> tasks = taskRepository
                    .findAll();
            entityManager.getTransaction().commit();
            if (tasks==null) throw new DataValidateException("Tasks not found");
            return tasks
                    .stream()
                    .map(Task::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            taskRepository
                    .removeAllByProjectAndUserId(getProject(id,entityManager),getUser(userId, entityManager));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void removeAllByUserId(
            @NotNull final String id)
            throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            taskRepository
                    .removeAllByUserId(getUser(id, entityManager));
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<TaskDTO> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter)
            throws DataValidateException {
        DataValidator.validateString(id, parameter);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Collection<Task> tasks = taskRepository
                    .sortAllByUserId(getUser(id,entityManager),parameter);
            entityManager.getTransaction().commit();
            if (tasks==null) throw new DataValidateException("Tasks not found");
            return tasks
                    .stream()
                    .map(Task::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<TaskDTO> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws DataValidateException {
        DataValidator.validateString(name, description, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Collection<Task> tasks = taskRepository
                    .findAllByPartOfNameOrDescription(name, description,getUser(userId,entityManager));
            entityManager.getTransaction().commit();
            if (tasks==null) throw new DataValidateException("Tasks not found");
            return tasks
                    .stream()
                    .map(Task::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<TaskDTO> findAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException {
        DataValidator.validateString(id, userId);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Collection<Task> tasks = taskRepository
                    .findAllByProjectAndUserId(getProject(id, entityManager), getUser(userId, entityManager));
            entityManager.getTransaction().commit();
            if (tasks==null) throw new DataValidateException("Tasks not found");
            return tasks
                    .stream()
                    .map(Task::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<TaskDTO> findAllByUserId(
            @NotNull final String id)
            throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ITaskRepository taskRepository = new TaskRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Collection<Task> tasks = taskRepository
                    .findAllByUserId(getUser(id,entityManager));
            entityManager.getTransaction().commit();
            if (tasks==null) throw new DataValidateException("Tasks not found");
            return tasks
                    .stream()
                    .map(Task::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private Task convertDTOtoTask(
            @NotNull final TaskDTO taskDTO,
            @NotNull final EntityManager entityManager)
            throws DataValidateException {
        @NotNull final Task task = new Task();
        task.setId(taskDTO.getId());
        task.setName(taskDTO.getName());
        task.setDescription(taskDTO.getDescription());
        task.setDateBegin(taskDTO.getDateBegin());
        task.setDateEnd(taskDTO.getDateEnd());
        task.setUser(getUser(taskDTO.getUserId(), entityManager));
        task.setProject(getProject(taskDTO.getProjectId(),entityManager));
        task.setStatus(taskDTO.getStatus());
        return task;
    }

    private User getUser(
            @NotNull final String id,
            @NotNull final EntityManager em)
            throws DataValidateException {
        @NotNull final IUserRepository userRepository = new UserRepository(em);
        @Nullable final User user = userRepository.findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }

    private Project getProject(
            @NotNull final String id,
            @NotNull final EntityManager em)
            throws DataValidateException{
        @NotNull final IProjectRepository projectRepository = new ProjectRepository(em);
        @Nullable final Project project = projectRepository.findOne(id);
        if (project == null) throw new DataValidateException("Project not found!");
        return project;
    }
}
