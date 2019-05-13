package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.StringValidator;

import java.util.*;

@AllArgsConstructor
public final class TaskService implements ITaskService {

    @NotNull
    final SqlSessionFactory sessionFactory;

    @Override
    public Task create(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID) {
        if (!StringValidator.validate(name, description, projectID, userID)) return null;
        @NotNull final Task task = new Task(name, description, projectID, userID);

        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).persist(task);
            session.commit();
            return task;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public Task edit(@NotNull String id, @NotNull String name, @NotNull String description, @NotNull String status) {
        if (!StringValidator.validate(id, name, description, status)) return null;
        if (EnumUtil.stringToStatus(status) == null) return null;
        @NotNull final Task task = findOne(id);
        task.setName(name);
        task.setDescription(description);
        task.setStatus(status);
        if (Status.DONE == EnumUtil.stringToStatus(status)) {
            task.setDateEnd(new Date());
        } else {
            task.setDateEnd(null);
        }
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).merge(task);
            session.commit();
            return task;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public Task findOne(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findOneByUserId(id, userId);
        }
    }

    @Override
    public Task remove(@NotNull String id, @NotNull String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        @Nullable final Task task = findOne(id, userId);
        if (task == null) return null;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).removeOneByUserId(id, userId);
            session.commit();
            return task;
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
            session.getMapper(ITaskRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Task findOne(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findOne(id);
        }
    }

    @Override
    public Task remove(@NotNull String id) {
        if (!StringValidator.validate(id)) return null;
        @Nullable final Task task = findOne(id);
        if (task == null) return null;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).remove(id);
            session.commit();
            return task;
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
        return null;
    }

    @Override
    public Collection<Task> findAll() {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAll();
        }
    }

    @Override
    public void removeAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).removeAllByProjectAndUserId(id, userId);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public void removeAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return;
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).removeAllByUserId(id);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Task> sortAllByUserId(@NotNull String id, @NotNull String parameter) {
        if (!StringValidator.validate(id, parameter)) return null;
        if (!ParameterValidator.validate(parameter)) return null;
        if ("order".equals(parameter)) return findAllByUserId(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).sortAllByUserId(id, parameter);
        }
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        if (!StringValidator.validate(name, description, userId)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByPartOfNameOrDescription(name, description, userId);
        }
    }

    @Override
    public Collection<Task> findAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        if (!StringValidator.validate(id, userId)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByProjectAndUserId(id, userId);
        }
    }

    @Override
    public Collection<Task> findAllByUserId(@NotNull final String id) {
        if (!StringValidator.validate(id)) return null;
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByUserId(id);
        }
    }
}
