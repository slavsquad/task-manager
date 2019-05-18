package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.persistence.EntityManager;
import java.util.Collection;

public interface ITaskService {

    void create(
            @NotNull final TaskDTO taskDTO)
            throws DataValidateException;

    void edit(
            @NotNull final TaskDTO taskDTO)
            throws DataValidateException;

    TaskDTO findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void clear()
            throws DataValidateException;

    TaskDTO findOne(
            @NotNull final String id)
            throws DataValidateException;

    void remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<TaskDTO> findAll()
            throws DataValidateException;

    Collection<TaskDTO> findAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    Collection<TaskDTO> findAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    void removeAllByProjectId(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void removeAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    Collection<TaskDTO> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator)
            throws DataValidateException;

    Collection<TaskDTO> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws DataValidateException;
}
