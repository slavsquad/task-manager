package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public enum UserRepository implements IUserRepository {

    INSTANCE;

    @NotNull
    private final Map<String, User> users;

    UserRepository() {
        users = new LinkedHashMap<>();
        generate();
    }

    private void generate() {
        @NotNull final User admin = new User(
                "admin",
                "admin",
                "Administrator",
                "Administrator for task manager application.",
                Role.ADMIN);
        @NotNull final User user = new User(
                "user",
                "user",
                "User",
                "User for task manager application.",
                Role.USER);
        admin.setId("1");
        user.setId("2");
        users.put(admin.getId(), admin);
        users.put(user.getId(), user);
    }

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
        for (@NotNull final User user : findAll()) {
            if (user.getLogin().equals(login))
                return user;
        }
        return null;
    }
}
