package ru.stepanenko.tm.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.api.service.IPropertyService;
import ru.stepanenko.tm.api.service.ISessionService;
import ru.stepanenko.tm.model.dto.SessionDTO;
import ru.stepanenko.tm.model.dto.UserDTO;
import ru.stepanenko.tm.model.entity.Session;
import ru.stepanenko.tm.model.entity.User;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.exception.AuthenticationSecurityException;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.util.SignatureUtil;
import ru.stepanenko.tm.util.DataValidator;

import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Service
public class SessionService implements ISessionService {

    @NotNull
    private final IPropertyService propertyService;

    @NotNull
    final IUserRepository userRepository;

    @NotNull
    final ISessionRepository sessionRepository;

    @Autowired
    public SessionService(
            @NotNull final IPropertyService propertyService,
            @NotNull final ISessionRepository sessionRepository,
            @NotNull final IUserRepository userRepository) {
        this.propertyService = propertyService;
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
    }

    @Override
    @Transactional
    public void clear(
    ) throws DataValidateException {
        @Nullable final Collection<Session> sessions = sessionRepository
                .findAll();
        if (sessions == null) throw new DataValidateException("Sessions not found!");
        sessions.forEach(sessionRepository::delete);
    }

    @Override
    @Transactional
    public SessionDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Session session = sessionRepository
                .findById(id).get();
        if (session == null) throw new DataValidateException("Session not found!");
        return session.getDTO();
    }

    @Override
    @Transactional
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @Nullable final Session session = sessionRepository
                .findById(id).get();
        if (session == null) throw new DataValidateException("Session not found!");
        sessionRepository
                .delete(session);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<SessionDTO> findAll(
    ) throws DataValidateException {
        @Nullable final Collection<Session> sessions = sessionRepository
                .findAll();
        if (sessions == null) throw new DataValidateException("Sessions not found!");
        return sessions
                .stream()
                .map(Session::getDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SessionDTO create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, false);
        @NotNull final String cycle = propertyService.getCycle();
        @NotNull final String salt = propertyService.getSalt();
        @NotNull final Session session = new Session();
        session.setTimestamp(new Date());
        session.setUser(getUser(userDTO.getId()));
        session.setSignature(SignatureUtil.sign(userDTO, salt, Integer.parseInt(cycle)));
        sessionRepository
                .save(session);
        return session.getDTO();
    }

    @Override
    @Transactional(readOnly = true)
    public void validate(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateSessionDTO(sessionDTO);
        if (!sessionDTO.getSignature().equals(findOne(sessionDTO.getId()).getSignature()))
            throw new AuthenticationSecurityException("SessionDTO is invalid: \nSessionDTO signature is wrong! Please re-login!");
    }

    @Override
    @Transactional(readOnly = true)
    public void validateAdmin(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        validate(sessionDTO);
        @NotNull User user = getUser(sessionDTO.getUserId());
        if (!user.getRole().equals(Role.ADMIN))
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }

    @Transactional(readOnly = true)
    private User getUser(
            @NotNull final String userId
    ) throws DataValidateException {
        @Nullable final User user = userRepository.findById(userId).get();
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }
}
