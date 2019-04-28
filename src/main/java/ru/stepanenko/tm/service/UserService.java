package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IUserService;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.HashUtil;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

public final class UserService extends AbstractEntityService<User, IUserRepository> implements IUserService {

    @Nullable
    private User currentUser;

    public UserService(@NotNull IUserRepository userRepository) {
        super(userRepository);
    }

    @Override
    public User create(@NotNull final String login, @NotNull final String password, @NotNull final String role) {
        if (!StringValidator.validate(login, password, role)) return null;
        if (EnumUtil.stringToRole(role) == null) return null;
        return repository.persist(new User(login, HashUtil.md5(password), EnumUtil.stringToRole(role)));
    }

    @Override
    public User edit(@NotNull final String id, @NotNull final String login, @NotNull final String password, @NotNull final String role) {
        if (!StringValidator.validate(id, login, password, role)) return null;
        if (EnumUtil.stringToRole(role) == null) return null;
        @NotNull
        User user = findOne(id);
        user.setLogin(login);
        user.setPassword(HashUtil.md5(password));
        user.setRole(EnumUtil.stringToRole(role));
        return repository.merge(user);
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public User findByLogin(@NotNull final String login) {
        if (!StringValidator.validate(login)) return null;
        return repository.findByLogin(login);
    }

    @Override
    public void setCurrentUser(@Nullable final User user) {
        this.currentUser = user;
    }

    @Override
    public boolean authenticationUser(@NotNull final String login, @NotNull final String password) {
        @Nullable
        User user = findByLogin(login);
        if (user == null || !HashUtil.md5(password).equals(user.getPassword())) return false;
        setCurrentUser(user);
        return true;
    }
}
