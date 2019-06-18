package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.model.dto.TaskDTO;
import ru.stepanenko.tm.exception.DataValidateException;

import java.util.Collection;

@Service
public interface ITaskService {

    @Transactional
    void create(
            @Nullable final TaskDTO taskDTO
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final TaskDTO taskDTO
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    TaskDTO findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void clear(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    TaskDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<TaskDTO> findAll(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<TaskDTO> findAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<TaskDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void removeAllByProjectId(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    @Transactional
    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<TaskDTO> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<TaskDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;
}