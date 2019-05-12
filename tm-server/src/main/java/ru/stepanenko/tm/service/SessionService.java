package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.util.SignatureUtil;
import ru.stepanenko.tm.util.StringValidator;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

public class SessionService implements ISessionService {

    @NotNull
    private final ISessionRepository repository;

    @NotNull
    private final IUserRepository userRepository;

    @NotNull
    private final IPropertyService propertyService;

    public SessionService(@NotNull final ISessionRepository sessionRepository,
                          @NotNull final IUserRepository userRepository,
                          @NotNull final IPropertyService propertyService) {
        this.repository = sessionRepository;
        this.userRepository = userRepository;
        this.propertyService = propertyService;
    }

    @Override
    public void clear() {
        repository.removeAll();
    }

    @Override
    public Session findOne(@NotNull String id) {
        if(!StringValidator.validate(id)) return null;
        return repository.findOne(id);
    }

    @Override
    public Session remove(@NotNull String id) {
        if(!StringValidator.validate(id)) return null;
        @Nullable Session session = findOne(id);
        if (session==null) return null;
        repository.remove(id);
        return session;
    }

    @Override
    public Collection<Session> findAll() {
        return repository.findAll();
    }

    @Override
    public Session create(@NotNull String userId) throws IOException {
        if(!StringValidator.validate(userId)) return null;
        @NotNull final String cycle = propertyService.getCycle();
        @NotNull final String salt = propertyService.getSalt();
        @NotNull final Session session = new Session();
        session.setTimestamp(new Date());
        session.setUserId(userId);
        session.setSignature(SignatureUtil.sign(session, salt, Integer.parseInt(cycle)));
        repository.persist(session);
        return findOne(session.getId());
    }

    @Override
    public void validate(@Nullable Session session) throws AuthenticationSecurityException {
        if (session == null)
            throw new AuthenticationSecurityException("Session is invalid: \nSession must not be null! Please re-login!");
        if (session.getSignature() == null)
            throw new AuthenticationSecurityException("Session is invalid: \nSignature must not be null! Please re-login!");
        if (session.getUserId() == null)
            throw new AuthenticationSecurityException("Session is invalid: \nUser must not be null! Please re-login!");
        if (session.getTimestamp() == null)
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
