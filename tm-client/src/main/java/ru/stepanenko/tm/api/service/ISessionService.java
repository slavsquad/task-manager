package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@ApplicationScoped
public interface ISessionService {
    SessionDTO getCurrentSession();

    void setCurrentSession(@Nullable final SessionDTO sessionDTO);
}
