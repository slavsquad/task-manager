package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;

import javax.persistence.EntityManager;
import java.util.Collection;

@AllArgsConstructor
public final class SessionRepository implements ISessionRepository {

    @NotNull
    final EntityManager entityManager;

    @Override
    public Session findOne(
            @NotNull final String id) {
        return entityManager.find(Session.class, id);
    }

    @Override
    public Collection<Session> findAll() {
        return entityManager.createQuery("SELECT e FROM Session e", Session.class).getResultList();
    }

    @Override
    public void removeAll() {
        @NotNull final Collection<Session> sessions = findAll();
        if (sessions == null) return;
        sessions.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final Session session) {
        entityManager.remove(session);
    }

    @Override
    @SneakyThrows
    public void persist(
            @NotNull final Session session) {
        entityManager.persist(session);
    }

    @Override
    public Session merge(
            @NotNull final Session session) {
        return entityManager.merge(session);
    }

    @Override
    public Collection<Session> findAllByUserId(
            @NotNull User user) {
        @NotNull final Collection<Session> sessions = entityManager
                .createQuery("SELECT e FROM Session e WHERE e.user = :user", Session.class)
                .setParameter("user", user)
                .getResultList();
        return sessions;
    }
}
