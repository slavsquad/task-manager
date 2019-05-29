package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.User;

@Repository
public interface IUserRepository extends JpaRepository<User, String> {

    User findByLogin(
            @NotNull final String login);
}
