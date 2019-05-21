package ru.stepanenko.tm.service;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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
import ru.stepanenko.tm.repository.SessionRepository;
import ru.stepanenko.tm.repository.UserRepository;
import ru.stepanenko.tm.util.SignatureUtil;
import ru.stepanenko.tm.util.DataValidator;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@ApplicationScoped
public class SessionService implements ISessionService {

    @NotNull
    private final EntityManagerFactory entityManagerFactory;

    @NotNull
    private final IPropertyService propertyService;

    @Inject
    public SessionService(
            @NotNull final EntityManagerFactory entityManagerFactory,
            @NotNull final IPropertyService propertyService) {
        this.entityManagerFactory = entityManagerFactory;
        this.propertyService = propertyService;
    }

    @Override
    public void clear(
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            sessionRepository
                    .removeAll();
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public SessionDTO findOne(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Session session = sessionRepository
                    .findOne(id);
            if (session == null) throw new DataValidateException("Session not found!");
            entityManager.getTransaction().commit();
            return session.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void remove(
            @Nullable final String id
    ) throws DataValidateException {
        DataValidator.validateString(id);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Session session = sessionRepository
                    .findOne(id);
            if (session == null) throw new DataValidateException("Session not found!");
            sessionRepository
                    .remove(session);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public Collection<SessionDTO> findAll(
    ) throws DataValidateException {
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @Nullable final Collection<Session> sessions = sessionRepository
                    .findAll();
            if (sessions == null) throw new DataValidateException("Sessions not found!");
            entityManager.getTransaction().commit();
            return sessions
                    .stream()
                    .map(Session::getDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public SessionDTO create(
            @Nullable final UserDTO userDTO
    ) throws DataValidateException {
        DataValidator.validateUserDTO(userDTO, false);
        @NotNull final String cycle = propertyService.getCycle();
        @NotNull final String salt = propertyService.getSalt();
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        @NotNull final ISessionRepository sessionRepository = new SessionRepository(entityManager);
        try {
            entityManager.getTransaction().begin();
            @NotNull final Session session = new Session();
            session.setTimestamp(new Date());
            session.setUser(getUser(userDTO.getId(), entityManager));
            session.setSignature(SignatureUtil.sign(userDTO, salt, Integer.parseInt(cycle)));
            sessionRepository
                    .persist(session);
            entityManager.getTransaction().commit();
            return session.getDTO();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void validate(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        DataValidator.validateSessionDTO(sessionDTO);
        if (!sessionDTO.getSignature().equals(findOne(sessionDTO.getId()).getSignature()))
            throw new AuthenticationSecurityException("SessionDTO is invalid: \nSessionDTO signature is wrong! Please re-login!");
    }

    @Override
    public void validateAdmin(
            @Nullable final SessionDTO sessionDTO
    ) throws AuthenticationSecurityException, DataValidateException {
        validate(sessionDTO);
        @NotNull final EntityManager entityManager = entityManagerFactory.createEntityManager();
        try {
            entityManager.getTransaction().begin();
            @NotNull User user = getUser(sessionDTO.getUserId(), entityManager);
            if (!user.getRole().equals(Role.ADMIN))
                throw new AuthenticationSecurityException("Forbidden action for your role!");
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            entityManager.getTransaction().rollback();
            throw new DataValidateException(e.getMessage());
        } finally {
            entityManager.close();
        }
    }

    private User getUser(
            @NotNull final String userId,
            @NotNull final EntityManager em
    ) throws DataValidateException {
        @NotNull final IUserRepository userRepository = new UserRepository(em);
        @Nullable final User user = userRepository.findOne(userId);
        if (user == null) throw new DataValidateException("User not found!");
        return user;
    }
}
