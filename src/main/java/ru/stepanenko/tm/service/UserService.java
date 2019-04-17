package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.HashCode;
import ru.stepanenko.tm.util.Role;
import ru.stepanenko.tm.util.StringValidator;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserService implements IUserService {
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
    public User create(String login, String password, String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        if (getUserRole(role) == null) return null;
        return userRepository.persist(new User(login, HashCode.md5(password), getUserRole(role)));
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
    public User edit(String id, String login, String password, String role) {
        if (!StringValidator.validate(id, login, password, role)) return null;
        if (getUserRole(role) == null) return null;
        User user = findById(id);
        user.setLogin(login);
        user.setPassword(HashCode.md5(password));
        user.setRole(getUserRole(role));
        return userRepository.merge(user);
    }

    @Override
    public User findById(String id) {
        if (!StringValidator.validate(id)) return null;
        return userRepository.findOne(id);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User findByLogin(String login) {
        if (!StringValidator.validate(login)) return null;
        Collection<User> userCollection = userRepository.findAll();
        for (User user : userCollection) if (login.equals(user.getLogin())) return user;
        return null;
    }

    @Override
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    @Override
    public boolean authenticationUser(String login, String password){
        User user = findByLogin(login);
        if (user == null || !HashCode.md5(password).equals(user.getPassword())) return false;
        setCurrentUser(user);
        return true;
    }

    private Role getUserRole(String role) {
        Map<String, Role> userRoles = new HashMap<>(2);
        userRoles.put("admin", Role.ADMINISTRATOR);
        userRoles.put("user", Role.USER);
        return userRoles.get(role);
    }
}
