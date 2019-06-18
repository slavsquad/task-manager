package ru.stepanenko.tm.api.service;


import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.exception.DataValidateException;

import java.util.Collection;

@Service
public interface IProjectService {

    @Transactional
    void create(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException;

    @Transactional
    void edit(
            @Nullable final ProjectDTO projectDTO
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    ProjectDTO findOne(
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
    ProjectDTO findOne(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> findAll(
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional
    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException;

    @Transactional(readOnly = true)
    Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;
}