package ru.stepanenko.tm.endpoint;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.endpoint.IUserEndpoint;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.exception.session.InvalidSessionException;

public class UserEndpoint implements IUserEndpoint {

    @NotNull
    private IUserService userService;

    @NotNull
    private ISessionService sessionService;

    public UserEndpoint(@NotNull IUserService userService, @NotNull ISessionService sessionService) {
        this.userService = userService;
        this.sessionService = sessionService;
    }

    @Override
    public User createUser(@NotNull Session session, @NotNull String login, @NotNull String password, @NotNull String role) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return null;
        return userService.create(login, password, role);
    }

    @Override
    public User editUser(@NotNull Session session, @NotNull String id, @NotNull String login, @NotNull String password, @NotNull String role) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return null;
        return userService.edit(id, login, password, role);
    }

    @Override
    public User findUserByLogin(@NotNull Session session, @NotNull String login) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return null;
        return userService.findByLogin(login);
    }

    @Override
    public User authenticationUser(@NotNull String login, @NotNull String password) throws InvalidSessionException {
        return userService.authenticationUser(login,password);
    }

    @Override
    public void loadUserData(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.loadData();
    }

    @Override
    public void saveUserData(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.saveData();
    }

    @Override
    public void loadUserDataJaxbXml(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.loadDataJaxbXml();
    }

    @Override
    public void saveUserDataJaxbXml(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.saveDataJaxbXml();
    }

    @Override
    public void loadUserDataFasterXml(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.loadDataFasterXml();
    }

    @Override
    public void saveUserDataFasterXml(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.saveDataFasterXml();
    }

    @Override
    public void loadUserDataJaxbJSON(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.loadDataJaxbJSON();
    }

    @Override
    public void saveUserDataJaxbJSON(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.saveDataJaxbJSON();
    }

    @Override
    public void loadUserDataFasterJSON(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.loadDataFasterJSON();
    }

    @Override
    public void saveUserDataFasterJSON(@NotNull Session session) throws InvalidSessionException {
        if (!sessionService.validateAdmin(session)) return ;
        userService.saveDataFasterJSON();
    }
}
