package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.User;
import java.util.Collection;

public interface IUserRepository {

    User findOne(String id);
    Collection<User> findAll();
    void removeAll();
    User remove(String login);
    User persist(User user);
    User merge(User user);
}
