package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

@AllArgsConstructor
public final class TaskRepository implements ITaskRepository {

    @NotNull
    private final EntityManager entityManager;

    @Override
    public Task findOne(
            @NotNull final String id){
        return entityManager.find(Task.class, id);
    }

    @Override
    public Collection<Task> findAll() {
        return entityManager.createQuery("SELECT e FROM Task e", Task.class).getResultList();
    }

    @Override
    public void removeAll() {
        @NotNull final Collection<Task> tasks = findAll();
        if (tasks==null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final Task task) {
        entityManager.remove(task);
    }

    @Override
    public void persist(
            @NotNull final Task task) {
        entityManager.persist(task);
    }

    @Override
    public Task merge(
            @NotNull final Task task) {
        return entityManager.merge(task);
    }


    @Override
    public Collection<Task> findAllByUserId(
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.user = :user";
        @NotNull final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("user", user)
                .getResultList();
        return tasks;
    }

    @Override
    public Collection<Task> findAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.project = :project AND e.user = :user";
        @NotNull final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("project", project)
                .setParameter("user", user)
                .getResultList();
        return tasks;
    }

    @Override
    public Task findOneByUserId(
            @NotNull final String id,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.id = :id AND e.user = :user";
        @Nullable final Task task = entityManager.createQuery(query, Task.class)
                .setParameter("id", id)
                .setParameter("user", user)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return task;
    }

    @Override
    @SneakyThrows
    public void removeAllByUserId(
            @NotNull final User user) {
        @NotNull final Collection<Task> tasks = findAllByUserId(user);
        if (tasks==null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public void removeAllByProjectAndUserId(
            @NotNull final Project project,
            @NotNull final User user) {
        @NotNull final Collection<Task> tasks = findAllByProjectAndUserId(project, user);
        if (tasks==null) return;
        tasks.forEach(entityManager::remove);
    }

    @Override
    public Collection<Task> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter) {
        @NotNull final CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        @NotNull final CriteriaQuery<Task> criteriaQuery = criteriaBuilder.createQuery(Task.class);
        @NotNull final Root<Task> projectRoot = criteriaQuery.from(Task.class);
        @NotNull final Predicate condition = criteriaBuilder.equal(projectRoot.get("user"),user);
        criteriaQuery.select(projectRoot).where(condition);
        criteriaQuery.orderBy(criteriaBuilder.desc(projectRoot.get("parameter")));
        @NotNull final TypedQuery<Task> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final User user) {
        @NotNull final String query = "SELECT e FROM Task e WHERE e.user = :user and (e.name like :name OR e.description LIKE :description)";
        @NotNull final List<Task> tasks = entityManager.createQuery(query, Task.class)
                .setParameter("user", user)
                .setParameter("name", "%" + name + "%")
                .setParameter("description", "%" + description + "%")
                .getResultList();
        return tasks;
    }
}
