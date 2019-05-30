package ru.stepanenko.tm.service;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.endpoint.*;

@Service
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
