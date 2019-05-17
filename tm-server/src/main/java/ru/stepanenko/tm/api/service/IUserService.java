package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.util.Domain;

import java.util.Collection;

public interface IUserService {


    User create(
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws DataValidateException;

    User create(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws DataValidateException;

    User edit(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws DataValidateException;

    User edit(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password)
            throws DataValidateException;

    User findByLogin(
            @NotNull final String login)
            throws DataValidateException;

    void clear()
            throws DataValidateException;

    User findOne(
            @NotNull final String id)
            throws DataValidateException;

    User remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<User> findAll()
            throws DataValidateException;

    User authenticationUser(
            @NotNull final String login,
            @NotNull final String password)
            throws AuthenticationSecurityException, DataValidateException;

    void loadData()
            throws DataValidateException;

    void saveData()
            throws DataValidateException;

    void loadDataJaxbXml()
            throws DataValidateException;

    void saveDataJaxbXml()
            throws DataValidateException;

    void loadDataFasterXml()
            throws DataValidateException;

    void saveDataFasterXml()
            throws DataValidateException;

    void loadDataJaxbJSON()
            throws DataValidateException;

    void saveDataJaxbJSON()
            throws DataValidateException;

    void loadDataFasterJSON()
            throws DataValidateException;

    void saveDataFasterJSON()
            throws DataValidateException;

    void loadAllDataFromDomain(
            @NotNull final Domain domain)
            throws DataValidateException;

    Domain saveAllDataToDomain()
            throws DataValidateException;
}
