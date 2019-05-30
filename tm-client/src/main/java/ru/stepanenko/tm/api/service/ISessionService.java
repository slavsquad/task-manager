package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import ru.stepanenko.tm.endpoint.*;

@Service
public interface ISessionService {

    SessionDTO getCurrentSession();

    void setCurrentSession(@Nullable final SessionDTO sessionDTO);
}
