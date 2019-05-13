package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.api.service.IProjectService;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.InputDataValidateException;
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.InputDataValidator;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public final class ProjectService implements IProjectService {

    @NotNull
    final SqlSessionFactory sessionFactory;

    @Override
    public Project create(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(name, description, userId);
        @NotNull Project project = new Project(name, description, userId);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).persist(project);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Project edit(
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String status,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, name, description, status);
        InputDataValidator.validateStatus(status);
        @Nullable final Project project = findOne(id, userId);
        project.setName(name);
        project.setDescription(description);
        project.setStatus(status);
        if (Status.DONE == EnumUtil.stringToStatus(status)) {
            project.setDateEnd(new Date());
        } else {
            project.setDateEnd(null);
        }
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).merge(project);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Project findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        try (SqlSession session = sessionFactory.openSession()) {
            @Nullable final Project project = session.getMapper(IProjectRepository.class).findOneByUserId(id, userId);
            if (project == null) throw new InputDataValidateException("Project does not found!");
            return project;
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Project remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        @Nullable final Project project = findOne(id, userId);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeOneByUserId(id, userId);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void clear()
            throws InputDataValidateException {
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Project findOne(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findOne(id);
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Project remove(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        @Nullable final Project project = findOne(id);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).remove(id);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Project> findAll()
            throws InputDataValidateException {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAll();
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Collection<Project> findAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAllByUserId(id);
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public void removeAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeAllByUserID(id);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Project> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, parameter);
        InputDataValidator.validateParameter(parameter);
        if ("order".equals(parameter)) return findAllByUserId(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).sortAllByUserId(id, parameter);
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(name, description, userId);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAllByPartOfNameOrDescription(name, description, userId);
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }
}
