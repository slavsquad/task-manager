package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.endpoint.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Singleton;

@ApplicationScoped
@NoArgsConstructor
public class SessionService implements ISessionService {
    @Nullable
    private SessionDTO sessionDTO;


    @Override
    public SessionDTO getCurrentSession() {
        return sessionDTO;
    }

    @Override
    public void setCurrentSession(
            @Nullable final SessionDTO sessionDTO) {
        this.sessionDTO = sessionDTO;
    }
}
