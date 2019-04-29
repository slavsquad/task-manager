package ru.stepanenko.tm.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.entity.Session;

import java.util.ArrayList;
import java.util.Collection;

public class SessionRepository extends AbstractRepository<Session> implements ISessionRepository {
    @Override
    public Collection<Session> findAllByUserId(@NotNull String id) {
        @NotNull
        Collection<Session> userSessions = new ArrayList<>();
        for (Session session : findAll()) {
            if (id.equals(session.getUserId())) {
                userSessions.add(session);
            }
        }
        return userSessions;
    }
}
