package ru.stepanenko.tm.repository;


import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;

import java.util.Collection;

public final class UserRepository extends AbstractRepository<User> implements IUserRepository {
    @Override
    public User findByLogin(final String login) {
        Collection<User> userCollection = findAll();
        for (User user : userCollection) if (login.equals(user.getLogin())) return user;
        return null;
    }
}
