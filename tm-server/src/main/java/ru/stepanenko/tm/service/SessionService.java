package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
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
import ru.stepanenko.tm.exception.InputDataValidateException;
import ru.stepanenko.tm.util.SignatureUtil;
import ru.stepanenko.tm.util.InputDataValidator;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public class SessionService implements ISessionService {

    @NotNull
    final SqlSessionFactory sessionFactory;
    @NotNull
    final IPropertyService propertyService;

    @Override
    public void clear()
            throws InputDataValidateException {
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ISessionRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Session findOne(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        try (SqlSession sqlSession = sessionFactory.openSession()) {
            @Nullable final Session session = sqlSession.getMapper(ISessionRepository.class).findOne(id);
            if (session == null) throw new InputDataValidateException("Session does not found!");
            return session;
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Session remove(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        @Nullable Session session = findOne(id);
        @Nullable SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            sqlSession.getMapper(ISessionRepository.class).remove(id);
            sqlSession.commit();
            return session;
        } catch (Exception e) {
            if (sqlSession != null) sqlSession.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (sqlSession != null) sqlSession.close();
        }
    }

    @Override
    public Collection<Session> findAll()
            throws InputDataValidateException {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ISessionRepository.class).findAll();
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Session create(
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(userId);
        @NotNull final String cycle = propertyService.getCycle();
        @NotNull final String salt = propertyService.getSalt();
        @NotNull final Session session = new Session();
        session.setTimestamp(new Date());
        session.setUserId(userId);
        session.setSignature(SignatureUtil.sign(session, salt, Integer.parseInt(cycle)));
        @Nullable SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            sqlSession.getMapper(ISessionRepository.class).persist(session);
            sqlSession.commit();
            return session;
        } catch (Exception e) {
            if (sqlSession != null) sqlSession.rollback();
        } finally {
            if (sqlSession != null) sqlSession.close();
        }
        return null;
    }

    @Override
    public void validate(
            @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException {
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
    public void validateAdmin(
            @Nullable final Session session)
            throws AuthenticationSecurityException, InputDataValidateException {
        validate(session);
        @Nullable User user = null;
        try (SqlSession sqlSession = sessionFactory.openSession()) {
            user = sqlSession.getMapper(IUserRepository.class).findOne(session.getUserId());
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
        if (user == null) throw new AuthenticationSecurityException("User does not found!");
        if (!user.getRole().equals(Role.ADMINISTRATOR.toString()))
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
