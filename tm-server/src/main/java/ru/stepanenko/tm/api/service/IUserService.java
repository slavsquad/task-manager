package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.util.Domain;

import java.util.Collection;

public interface IUserService {


    User create(@NotNull final String login, @NotNull final String password, @NotNull final String role);
    User create(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role);
    User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password);
    User findByLogin(@NotNull final String login);
    void clear();
    User findOne(@NotNull final String id);
    User remove(@NotNull final String id);
    Collection<User> findAll();
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
    void loadAllDataFromDomain(@NotNull final Domain domain);
    Domain saveAllDataToDomain();
}
