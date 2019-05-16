package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.InputDataValidateException;
import ru.stepanenko.tm.util.Domain;

import java.util.Collection;

public interface IUserService {


    User create(
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws InputDataValidateException;

    User create(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws InputDataValidateException;

    User edit(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password,
            @NotNull final String role)
            throws InputDataValidateException;

    User edit(
            @NotNull final String id,
            @NotNull final String login,
            @NotNull final String password)
            throws InputDataValidateException;

    User findByLogin(
            @NotNull final String login)
            throws InputDataValidateException;

    void clear()
            throws InputDataValidateException;

    User findOne(
            @NotNull final String id)
            throws InputDataValidateException;

    User remove(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<User> findAll()
            throws InputDataValidateException;

    User authenticationUser(
            @NotNull final String login,
            @NotNull final String password)
            throws AuthenticationSecurityException, InputDataValidateException;

    void loadData()
            throws InputDataValidateException;

    void saveData()
            throws InputDataValidateException;

    void loadDataJaxbXml()
            throws InputDataValidateException;

    void saveDataJaxbXml()
            throws InputDataValidateException;

    void loadDataFasterXml()
            throws InputDataValidateException;

    void saveDataFasterXml()
            throws InputDataValidateException;

    void loadDataJaxbJSON()
            throws InputDataValidateException;

    void saveDataJaxbJSON()
            throws InputDataValidateException;

    void loadDataFasterJSON()
            throws InputDataValidateException;

    void saveDataFasterJSON()
            throws InputDataValidateException;

    void loadAllDataFromDomain(
            @NotNull final Domain domain)
            throws InputDataValidateException;

    Domain saveAllDataToDomain()
            throws InputDataValidateException;
}
