package ru.stepanenko.tm.api.repository;

import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;

import java.util.Collection;

@Repository
public interface ISessionRepository extends EntityRepository<Session, String> {

    Collection<Session> findByUser(
            @NotNull final User user);
}
