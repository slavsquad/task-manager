package ru.stepanenko.tm.service;

import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.RoleUtil;
import ru.stepanenko.tm.util.StringValidator;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {
    private User currentUser;

    public UserService(IUserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public User create(final String login, final String password, final String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        if (RoleUtil.stringToRole(role) == null) return null;
        return repository.persist(new User(login, HashUtil.md5(password), RoleUtil.stringToRole(role)));
    }

    @Override
    public User edit(final String id, final String login, final String password, final String role) {
        if (!StringValidator.validate(id, login, password, role)) return null;
        if (RoleUtil.stringToRole(role) == null) return null;
        User user = findOne(id);
        user.setLogin(login);
        user.setPassword(HashUtil.md5(password));
        user.setRole(RoleUtil.stringToRole(role));
        return repository.merge(user);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User findByLogin(final String login) {
        if (!StringValidator.validate(login)) return null;
        return repository.findByLogin(login);
    }

    @Override
    public void setCurrentUser(final User user) {
        this.currentUser = user;
    }

    @Override
    public boolean authenticationUser(final String login, final String password) {
        User user = findByLogin(login);
        if (user == null || !HashUtil.md5(password).equals(user.getPassword())) return false;
        setCurrentUser(user);
        return true;
    }
}
