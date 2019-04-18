package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.util.StringValidator;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public final class UserService implements IUserService {
    private IUserRepository userRepository;
    private User currentUser;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void clear() {
        userRepository.removeAll();
    }

    @Override
    public User create(final String login, final String password, final String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        if (getUserRole(role) == null) return null;
        return userRepository.persist(new User(login, HashUtil.md5(password), getUserRole(role)));
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User remove(final String login) {
        if (!StringValidator.validate(login)) return null;
        return userRepository.remove(login);
    }


    @Override
    public User edit(final String id, final String login, final String password, final String role) {
        if (!StringValidator.validate(id, login, password, role)) return null;
        if (getUserRole(role) == null) return null;
        User user = findById(id);
        user.setLogin(login);
        user.setPassword(HashUtil.md5(password));
        user.setRole(getUserRole(role));
        return userRepository.merge(user);
    }

    @Override
    public User findById(final String id) {
        if (!StringValidator.validate(id)) return null;
        return userRepository.findOne(id);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User findByLogin(final String login) {
        if (!StringValidator.validate(login)) return null;
        return userRepository.findByLogin(login);
    }

    @Override
    public void setCurrentUser(final User user) {
        this.currentUser = user;
    }

    @Override
    public boolean authenticationUser(final String login, final String password){
        User user = findByLogin(login);
        if (user == null || !HashUtil.md5(password).equals(user.getPassword())) return false;
        setCurrentUser(user);
        return true;
    }

    private Role getUserRole(final String role) {
        Map<String, Role> userRoles = new HashMap<>(2);
        userRoles.put("admin", Role.ADMINISTRATOR);
        userRoles.put("user", Role.USER);
        return userRoles.get(role);
    }
}
