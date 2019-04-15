package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;

public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void clear() {
        userRepository.removeAll();
    }

    @Override
    public User create(String login, String password, String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        return userRepository.persist(new User(login, password, role));
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User remove(String login) {
        if (!StringValidator.validate(login)) return null;
        return userRepository.remove(login);
    }

    @Override
    public User edit(String login, String password, String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        User user = findOne(login);
        user.setPassword(password);
        user.setRole(role);
        return userRepository.merge(user);
    }

    @Override
    public User findOne(String login) {
        if (!StringValidator.validate(login)) return null;
        return userRepository.findOne(login);
    }
}
