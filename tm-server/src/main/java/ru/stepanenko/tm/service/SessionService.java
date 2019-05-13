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
import ru.stepanenko.tm.util.SignatureUtil;
import ru.stepanenko.tm.util.StringValidator;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public class SessionService implements ISessionService {

    @NotNull
    final SqlSessionFactory sessionFactory;
    @NotNull
    final IPropertyService propertyService;

    @Override
    public void clear() {
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ISessionRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Session findOne(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ISessionRepository.class).findOne(id);
        }
    }

    @Override
    public Session remove(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        @Nullable Session session = findOne(id);
        if (session == null) return null;
        @Nullable SqlSession sqlSession = null;
        try {
            sqlSession = sessionFactory.openSession();
            sqlSession.getMapper(ISessionRepository.class).remove(id);
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
    public Collection<Session> findAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ISessionRepository.class).findAll();
        }
    }

    @Override
    public Session create(@NotNull String userId) throws IOException {
        if (!StringValidator.validate(userId)) return null;
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
        @Nullable User user = null;
        try (SqlSession sqlSession = sessionFactory.openSession()) {
            user = sqlSession.getMapper(IUserRepository.class).findOne(session.getUserId());
        }
        if (user == null) throw new AuthenticationSecurityException("User does not found!");
        if (!user.getRole().equals(Role.ADMINISTRATOR.toString()))
            throw new AuthenticationSecurityException("Forbidden action for your role!");
    }
}
