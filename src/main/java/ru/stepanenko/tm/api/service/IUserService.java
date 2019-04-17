package ru.stepanenko.tm.api.service;

import ru.stepanenko.tm.entity.User;


import java.security.NoSuchAlgorithmException;
import java.util.Collection;

public interface IUserService {

    void clear();
    User create(String login, String password, String role);
    Collection<User> findAll();
    User remove(String login);
    User edit(String id, String login, String password, String role);
    User findById(String id);
    User getCurrentUser();
    User findByLogin(String login);
    void setCurrentUser(User user);
    boolean authenticationUser(String login, String password);
}
