package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.User;
import java.util.Collection;

public interface IUserRepository {

    User findOne(final String id);
    Collection<User> findAll();
    void removeAll();
    User remove(final String login);
    User persist(final User user);
    User merge(final User user);
}
