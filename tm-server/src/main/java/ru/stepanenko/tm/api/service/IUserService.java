package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.LoginOrPasswordEmpty;
import ru.stepanenko.tm.exception.WrongLoginOrPasswordException;

public interface IUserService extends IAbstractEntityService<User> {

    User create(@NotNull final String login, @NotNull final String password, @NotNull final String role);
    User create(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User getCurrentUser();
    User findByLogin(@NotNull final String login);
    void setCurrentUser(@Nullable final User user);
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
