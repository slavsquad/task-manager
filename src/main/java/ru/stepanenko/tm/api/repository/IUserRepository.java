package ru.stepanenko.tm.api.repository;

import ru.stepanenko.tm.entity.User;

public interface IUserRepository extends IAbstractRepository<User> {
    User findByLogin(final String login);
}
