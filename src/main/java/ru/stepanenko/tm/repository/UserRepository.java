package ru.stepanenko.tm.repository;


import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {
    Map<String,User> users = new HashMap<>();

    @Override
    public User findOne(String login) {
        return users.get(login);
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
    public User remove(String login) {
        return users.remove(login);
    }

    @Override
    public User persist(User user) {
        return merge(user);
    }

    @Override
    public User merge(User user) {
        users.put(user.getLogin(),user);
        return user;
    }
}
