package ru.stepanenko.tm.api.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Repository
public interface IUserRepository extends EntityRepository<User, String> {

    User findAnyByLogin(
            @NotNull final String login);
}
