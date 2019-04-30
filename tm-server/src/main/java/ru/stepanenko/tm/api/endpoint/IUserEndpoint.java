package ru.stepanenko.tm.api.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

public interface IUserEndpoint {

    User createUser(@NotNull final Session session, @NotNull final String login, @NotNull final String password, @NotNull final String role) throws InvalidSessionException;
    User editUser(@NotNull final Session session, @NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role) throws InvalidSessionException;
    User findUserByLogin(@NotNull final Session session, @NotNull final String login) throws InvalidSessionException;
    User authenticationUser(@NotNull final String login, @NotNull final String password) throws InvalidSessionException;
    void loadUserData(@NotNull final Session session) throws InvalidSessionException;
    void saveUserData(@NotNull final Session session) throws InvalidSessionException;
    void loadUserDataJaxbXml(@NotNull final Session session) throws InvalidSessionException;
    void saveUserDataJaxbXml(@NotNull final Session session) throws InvalidSessionException;
    void loadUserDataFasterXml(@NotNull final Session session) throws InvalidSessionException;
    void saveUserDataFasterXml(@NotNull final Session session) throws InvalidSessionException;
    void loadUserDataJaxbJSON(@NotNull final Session session) throws InvalidSessionException;
    void saveUserDataJaxbJSON(@NotNull final Session session) throws InvalidSessionException;
    void loadUserDataFasterJSON(@NotNull final Session session) throws InvalidSessionException;
    void saveUserDataFasterJSON(@NotNull final Session session) throws InvalidSessionException;
}