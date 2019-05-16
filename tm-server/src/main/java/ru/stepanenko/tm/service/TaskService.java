package ru.stepanenko.tm.service;

import lombok.AllArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.service.ITaskService;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.exception.InputDataValidateException;
import ru.stepanenko.tm.util.ParameterValidator;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.InputDataValidator;

import java.util.*;

@AllArgsConstructor
public final class TaskService implements ITaskService {

    @NotNull
    final SqlSessionFactory sessionFactory;

    @Override
    public Task create(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String projectID,
            @NotNull final String userID)
            throws InputDataValidateException {
        InputDataValidator.validateString(name, description, projectID, userID);
        @NotNull final Task task = new Task(name, description, projectID, userID);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).persist(task);
            session.commit();
            return task;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Task edit(
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String status)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, name, description, status);
        InputDataValidator.validateStatus(status);
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
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Task findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        try (SqlSession session = sessionFactory.openSession()) {
            @Nullable final Task task = session.getMapper(ITaskRepository.class).findOneByUserId(id, userId);
            if (task == null) throw new InputDataValidateException("TaskDTO does not found!");
            return task;
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Task remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        @Nullable final Task task = findOne(id, userId);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).removeOneByUserId(id, userId);
            session.commit();
            return task;
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
            session.getMapper(ITaskRepository.class).removeAll();
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Task findOne(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findOne(id);
        }catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Task remove(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        @Nullable final Task task = findOne(id);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).remove(id);
            session.commit();
            return task;
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Task> findAll()
            throws InputDataValidateException {
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAll();
        } catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public void removeAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        @Nullable SqlSession session = null;
        try {
            session = sessionFactory.openSession();
            session.getMapper(ITaskRepository.class).removeAllByProjectAndUserId(id, userId);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
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
            session.getMapper(ITaskRepository.class).removeAllByUserId(id);
            session.commit();
        } catch (Exception e) {
            if (session != null) session.rollback();
            throw new InputDataValidateException(e.getMessage());
        } finally {
            if (session != null) session.close();
        }
    }

    @Override
    public Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, parameter);
        if (!ParameterValidator.validate(parameter)) throw new InputDataValidateException("Incorrect input parameter for task's sorting!");
        if ("order".equals(parameter)) return findAllByUserId(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).sortAllByUserId(id, parameter);
        }catch (Exception e){
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(name, description, userId);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByPartOfNameOrDescription(name, description, userId);
        }catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Collection<Task> findAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException {
        InputDataValidator.validateString(id, userId);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByProjectAndUserId(id, userId);
        }catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }

    @Override
    public Collection<Task> findAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException {
        InputDataValidator.validateString(id);
        try (SqlSession session = sessionFactory.openSession()) {
            return session.getMapper(ITaskRepository.class).findAllByUserId(id);
        }catch (Exception e) {
            throw new InputDataValidateException(e.getMessage());
        }
    }
}
