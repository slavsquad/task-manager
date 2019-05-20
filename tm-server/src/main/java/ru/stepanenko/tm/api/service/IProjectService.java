package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.persistence.EntityManager;
import java.util.Collection;

public interface IProjectService {

    void create(
            @Nullable final ProjectDTO projectDTO)
            throws DataValidateException;

    void edit(
            @Nullable final ProjectDTO projectDTO)
            throws DataValidateException;

    ProjectDTO findOne(
            @Nullable final String id,
            @Nullable final String userId)
            throws DataValidateException;

    void remove(
            @Nullable final String id,
            @Nullable final String userId)
            throws DataValidateException;

    void clear()
            throws DataValidateException;

    ProjectDTO findOne(
            @Nullable final String id)
            throws DataValidateException;

    void remove(
            @Nullable final String id)
            throws DataValidateException;

    Collection<ProjectDTO> findAll()
            throws DataValidateException;

    Collection<ProjectDTO> findAllByUserId(
            @Nullable final String id)
            throws DataValidateException;

    void removeAllByUserId(
            @Nullable final String id)
            throws DataValidateException;

    Collection<ProjectDTO> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter)
            throws DataValidateException;

    Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId)
            throws DataValidateException;
}
