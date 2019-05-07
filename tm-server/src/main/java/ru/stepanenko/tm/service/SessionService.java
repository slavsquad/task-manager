package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.util.SignatureUtil;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class SessionService extends AbstractEntityService<Session, ISessionRepository> implements ISessionService {

    @NotNull
    private final IUserRepository userRepository;

    @NotNull private final Properties properties;

    public SessionService(@NotNull final ISessionRepository repository,
                          @NotNull final IUserRepository userRepository,
                          @NotNull final Properties properties) {
        super(repository);
        this.userRepository = userRepository;
        this.properties = properties;
    }

    @Override
    public Session create(@NotNull String userId) throws IOException {
        @NotNull final String cycle = properties.getProperty("cycle");
        @NotNull final String salt = properties.getProperty("salt");
        @NotNull final Session session = new Session();
        session.setTimeStamp(new Date());
        session.setUserId(userId);
        session.setSignature(SignatureUtil.sign(session, salt, Integer.parseInt(cycle)));
        return repository.persist(session);
    }

    @Override
    public void validate(@Nullable Session session) throws AuthenticationSecurityException {
        if (session == null)
            throw new AuthenticationSecurityException("Session is invalid: \nSession must not be null! Please re-login!");
        if (session.getSignature() == null)
            throw new AuthenticationSecurityException("Session is invalid: \nSignature must not be null! Please re-login!");
        if (session.getUserId() == null)
            throw new AuthenticationSecurityException("Session is invalid: \nUser must not be null! Please re-login!");
        if (session.getTimeStamp() == null)
            throw new AuthenticationSecurityException("Session is invalid: \nTime must not be null! Please re-login!");
        if (findOne(session.getId()) == null)
            throw new AuthenticationSecurityException("Session is invalid: \nSession not found! Please re-login!");
        if (!session.getSignature().equals(findOne(session.getId()).getSignature()))
            throw new AuthenticationSecurityException("Session is invalid: \nSession signature is wrong! Please re-login!");
    }

    @Override
    public void validateAdmin(@Nullable Session session) throws AuthenticationSecurityException {
        validate(session);
        User user = userRepository.findOne(session.getUserId());
        if (!user.getRole().equals(Role.ADMINISTRATOR))
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
