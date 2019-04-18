package ru.stepanenko.tm.repository;


import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class UserRepository implements IUserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public User findOne(final String id) {
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
    public User remove(final String id) {
        return users.remove(id);
    }

    @Override
    public User persist(final User user) {
        return merge(user);
    }

    @Override
    public User merge(final User user) {
        users.put(user.getId(), user);
        return user;
    }
}
