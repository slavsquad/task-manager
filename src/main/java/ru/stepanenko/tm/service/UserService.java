package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.DataValidator;

import java.util.Collection;

@Service
public class UserService implements IUserService {

    @NotNull
    private final IUserRepository userRepository;

    @Autowired
    public UserService(@NotNull final IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void create(
            @Nullable final User user
    ) throws DataValidateException {
        DataValidator.validateUser(user, true);
        userRepository
                .persist(user);
    }

    @Override
    public void edit(
            @Nullable final User user
    ) throws DataValidateException {
        DataValidator.validateUser(user, true);
        @NotNull final User findUser = userRepository
                .findByLogin(user.getLogin());

        if (findUser != null && user.getLogin().equals(findUser.getLogin()) && !(user.getId().equals(findUser.getId())))
            throw new DataValidateException("User with login: '" + user.getLogin() + "' already exist!");
        userRepository
                .merge(user);
    }

    @Override
    public User findByLogin(
            @Nullable final String login
    ) throws DataValidateException {
        DataValidator.validateString(login);
        @Nullable final User user = userRepository
                .findByLogin(login);
        if (user == null) throw new DataValidateException("User not found");
        return user;
    }

    @Override
    public User findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final User user = userRepository
                .findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable User user = userRepository
                .findOne(id);
        if (user == null) throw new DataValidateException("User not found!");
        userRepository
                .remove(user.getId());
    }

    public void clear(
    ) throws DataValidateException {
        userRepository.removeAll();
    }

    @Override
    public Collection<User> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<User> users = userRepository
                .findAll();
        if (users == null) throw new DataValidateException("Users not found!");
        return users;
    }

    @Override
    public User authenticationUser(
            @Nullable final String login,
            @Nullable final String password
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateString(login, password);
        @Nullable final User user = userRepository
                .findByLogin(login);
        if (user == null) throw new AuthenticationSecurityException("Wrong user name!");
        if (!user.getPassword().equals(password)) throw new AuthenticationSecurityException("Wrong password!");
        return user;
    }

}
