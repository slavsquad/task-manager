package ru.stepanenko.tm.repository;


import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;

public final class UserRepository extends AbstractRepository<User> implements IUserRepository {

    @Override
    public User findByLogin(@NotNull final String login) {
        for (User user : findAll()) if (login.equals(user.getLogin())) return user;
        return null;
    }
}
