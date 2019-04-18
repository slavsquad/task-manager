package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.User;
import java.util.Collection;

public interface IUserService {

    void clear();
    User create(final String login, final String password, final String role);
    Collection<User> findAll();
    User remove(final String login);
    User edit(final String id, final String login, final String password, final String role);
    User findById(final String id);
    User getCurrentUser();
    User findByLogin(final String login);
    void setCurrentUser(final User user);
    boolean authenticationUser(final String login, final String password);
}
