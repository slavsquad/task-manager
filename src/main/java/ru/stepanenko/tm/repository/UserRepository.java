package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {

    @NotNull final Map<String, User> users = new HashMap<>();

    @Override
    public User findOne(
            @NotNull final String id) {
        return users.get(id);
    }

    @Override
    public Collection<User> findAll() {
        return users.values();
    }

    @Override
    public void removeAll() {
        users.clear();
    }

    @Override
    public void remove(
            @NotNull final String id) {
        users.remove(id);
    }

    @Override
    public void persist(
            @NotNull final User user) {
        merge(user);
    }

    @Override
    public void merge(
            @NotNull final User user) {
        users.put(user.getId(), user);
    }

    @Override
    public User findByLogin(
            @NotNull final String login) {
        for (@NotNull final User user: findAll()){
            if (user.getLogin().equals(login))
                return user;
        }
        return null;
    }
}
