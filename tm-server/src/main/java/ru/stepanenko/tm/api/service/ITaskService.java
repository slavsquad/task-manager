package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.persistence.EntityManager;
import java.util.Collection;

public interface ITaskService {

    void create(
            @Nullable final TaskDTO taskDTO
    ) throws DataValidateException;

    void edit(
            @Nullable final TaskDTO taskDTO
    ) throws DataValidateException;

    TaskDTO findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void clear(
    ) throws DataValidateException;

    TaskDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<TaskDTO> findAll(
    ) throws DataValidateException;

    Collection<TaskDTO> findAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    Collection<TaskDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<TaskDTO> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator
    ) throws DataValidateException;

    Collection<TaskDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;
}
