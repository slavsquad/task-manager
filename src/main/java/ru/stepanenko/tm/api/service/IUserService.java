package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.User;
import java.util.Collection;

public interface IUserService {

    void clear();
    User create(String login, String password, String role);
    Collection<User> findAll();
    User remove(String login);
    User edit(String login, String password, String role);
    User findOne(String login);
}
