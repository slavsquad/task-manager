package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.exception.DataValidateException;

import javax.persistence.EntityManager;
import java.util.Collection;

public interface IProjectService {

    void create(
            @NotNull final ProjectDTO projectDTO)
            throws DataValidateException;

    void edit(
            @NotNull final ProjectDTO projectDTO)
            throws DataValidateException;

    ProjectDTO findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws DataValidateException;

    void clear()
            throws DataValidateException;

    ProjectDTO findOne(
            @NotNull final String id)
            throws DataValidateException;

    void remove(
            @NotNull final String id)
            throws DataValidateException;

    Collection<ProjectDTO> findAll()
            throws DataValidateException;

    Collection<ProjectDTO> findAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    void removeAllByUserId(
            @NotNull final String id)
            throws DataValidateException;

    Collection<ProjectDTO> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter)
            throws DataValidateException;

    Collection<ProjectDTO> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws DataValidateException;
}
