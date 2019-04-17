package ru.stepanenko.tm.repository;


import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public User findOne(String id) {
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
    public User remove(String id) {
        return users.remove(id);
    }

    @Override
    public User persist(User user) {
        return merge(user);
    }

    @Override
    public User merge(User user) {
        users.put(user.getId(), user);
        return user;
    }
}
