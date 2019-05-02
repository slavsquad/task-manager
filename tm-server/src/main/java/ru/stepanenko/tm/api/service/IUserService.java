package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;

public interface IUserService extends IAbstractEntityService<User> {

    User create(@NotNull final String login, @NotNull final String password, @NotNull final String role);
    User create(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password);
    User findByLogin(@NotNull final String login);
    User authenticationUser(@NotNull final String login, @NotNull final String password) throws AuthenticationSecurityException;
    void loadData();
    void saveData();
    void loadDataJaxbXml();
    void saveDataJaxbXml();
    void loadDataFasterXml();
    void saveDataFasterXml();
    void loadDataJaxbJSON();
    void saveDataJaxbJSON();
    void loadDataFasterJSON();
    void saveDataFasterJSON();
}
