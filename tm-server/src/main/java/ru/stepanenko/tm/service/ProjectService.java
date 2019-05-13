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
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

import java.util.Collection;
import java.util.Date;

@AllArgsConstructor
public final class ProjectService implements IProjectService {

    @NotNull
    final SqlSessionFactory sessionFactory;

    @Override
    public Project create(@NotNull final String name, @NotNull final String description, @NotNull final String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        @NotNull Project project = new Project(name, description, userId);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).persist(project);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public Project edit(@NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status, @NotNull String userId) {
        if (!StringValidator.validate(id, name, description, status)) return null;
        if (EnumUtil.stringToStatus(status) == null) return null;
        @Nullable final Project project = findOne(id, userId);
        if (project == null) return null;
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
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public Project findOne(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findOneByUserId(id, userId);
        }
    }

    @Override
    public Project remove(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        @Nullable final Project project = findOne(id, userId);
        if (project == null) return null;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeOneByUserId(id, userId);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public void clear() {
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Project findOne(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findOne(id);
        }
    }

    @Override
    public Project remove(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        @Nullable final Project project = findOne(id);
        if (project == null) return null;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).remove(id);
            session.commit();
            return project;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return project;
    }

    @Override
    public Collection<Project> findAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAll();
        }
    }

    @Override
    public Collection<Project> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAllByUserId(id);
        }
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(IProjectRepository.class).removeAllByUserID(id);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Project> sortAllByUserId(@NotNull String id, @NotNull String parameter) {
        if (!StringValidator.validate(id, parameter)) return null;
        if (!ParameterValidator.validate(parameter)) return null;
        if ("order".equals(parameter)) return findAllByUserId(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).sortAllByUserId(id, parameter);
        }
    }

    @Override
    public Collection<Project> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(IProjectRepository.class).findAllByPartOfNameOrDescription(name, description, userId);
        }
    }
}
