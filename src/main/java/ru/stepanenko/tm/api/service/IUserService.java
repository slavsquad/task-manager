package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.User;

public interface IUserService extends IAbstractEntityService<User> {

    User create(final String login, final String password, final String role);
    User edit(final String id, final String login, final String password, final String role);
    User getCurrentUser();
    User findByLogin(final String login);
    void setCurrentUser(final User user);
    boolean authenticationUser(final String login, final String password);
}
