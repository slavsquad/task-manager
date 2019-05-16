package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@AllArgsConstructor
public final class ProjectRepository implements IProjectRepository {

    @NotNull
    private final EntityManager entityManager;


    @Override
    @Nullable
    public Project findOne(
            @NotNull final String id) {
        return entityManager.find(Project.class, id);
    }

    @Override
    @Nullable
    public Collection<Project> findAll() {
        return entityManager.createQuery("SELECT e FROM Project e", Project.class).getResultList();
    }

    @Override
    @Nullable
    public void removeAll() {
        Collection<Project> projects = findAll();
        if (projects == null) return;
        projects.forEach(entityManager::remove);
    }

    @Override
    @Nullable
    public void remove(
            @NotNull final Project project) {
        entityManager.remove(project);
    }

    @Override
    @Nullable
    @SneakyThrows
    public void persist(
            @NotNull final Project project) {
        entityManager.persist(project);
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project merge(
            @NotNull final Project project) {
        return entityManager.merge(project);
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> findAllByUserId(
            @NotNull final User user) {
        @NotNull final Collection<Project> result = entityManager
                .createQuery("SELECT e FROM Project WHERE e.user = :user", Project.class)
                .setParameter("user", user)
                .getResultList();
        return result;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project findOneByUserId(
            @NotNull final String id,
            @NotNull final User user) {
        @Nullable final Project project = (Project) entityManager
                .createQuery("SELECT e FROM Project WHERE e.id = id AND e.user = :user", Project.class)
                .setParameter("id", id)
                .setParameter("user", user)
                .getSingleResult();
        return project;
    }

    @Override
    @Nullable
    public void removeAllByUserID(@NotNull final User user) {
        @Nullable final Collection<Project> projects = findAllByUserId(user);
        if (projects == null) return;
        projects.forEach(entityManager::remove);
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> sortAllByUserId(
            @NotNull final User user,
            @NotNull final String parameter) {
        @NotNull final String query = "SELECT e FROM Project WHERE e.user = :user ORDER BY  DESC";
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Project> q = cb.createQuery(Project.class);
        Root<Project> c = q.from(Project.class);
        q.select(c).where(cb.equal());
        q.orderBy(cb.desc(c.get("parameter")));
        @NotNull final Collection<Project> result = entityManager
                .createNativeQuery("SELECT e FROM Project WHERE user = :user ORDER BY ?", Project.class)
                .setParameter("user", user)
                .getResultList();


        return result;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId) {
        @NotNull final String query = "SELECT * FROM app_project WHERE user_id = ? and (name LIKE ? OR description LIKE ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userId);
        statement.setString(2, name);
        statement.setString(3, description);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Project> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }
}
