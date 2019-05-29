package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Repository
public interface ISessionRepository extends JpaRepository<Session, String> {

    Collection<Session> findByUser(
            @NotNull final User user);
}
