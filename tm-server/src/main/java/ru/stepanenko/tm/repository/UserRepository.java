package ru.stepanenko.tm.repository;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.model.entity.User;

import javax.persistence.EntityManager;
import java.util.Collection;

@AllArgsConstructor
public final class UserRepository implements IUserRepository {

    @NotNull
    private final EntityManager entityManager;

    @Override
    public User findOne(
            @NotNull final String id) {
        return entityManager.find(User.class, id);
    }

    @Override
    @SneakyThrows
    public Collection<User> findAll() {
        return entityManager.createQuery("SELECT e FROM User e", User.class).getResultList();
    }

    @Override
    @SneakyThrows
    public void removeAll() {
        @Nullable final Collection<User> users = findAll();
        if (users == null) return;
        users.forEach(entityManager::remove);
    }

    @Override
    public void remove(
            @NotNull final User user) {
        entityManager.remove(user);
    }

    @Override
    @SneakyThrows
    public void persist(
            @NotNull final User user) {
        entityManager.persist(user);
    }

    @Override
    @SneakyThrows
    public User merge(
            @NotNull final User user) {
        return entityManager.merge(user);
    }

    @Override
    @SneakyThrows
    public User findByLogin(
            @NotNull final String login) {
        @Nullable final User user = entityManager
                .createQuery("SELECT e FROM User e WHERE e.login = :login", User.class)
                .setParameter("login", login)
                .getResultList()
                .stream()
                .findFirst()
                .orElse(null);
        return user;
    }
}
