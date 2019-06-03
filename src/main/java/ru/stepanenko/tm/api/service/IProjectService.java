package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.exception.DataValidateException;
import ru.stepanenko.tm.model.entity.Project;

import java.util.Collection;

public interface IProjectService {

    void create(
            @Nullable final Project project
    ) throws DataValidateException;

    void edit(
            @Nullable final Project project
    ) throws DataValidateException;

    Project findOne(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void remove(
            @Nullable final String id,
            @Nullable final String userId
    ) throws DataValidateException;

    void clear(
    ) throws DataValidateException;

    Project findOne(
            @Nullable final String id
    ) throws DataValidateException;

    void remove(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<Project> findAll(
    ) throws DataValidateException;

    Collection<Project> findAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    void removeAllByUserId(
            @Nullable final String id
    ) throws DataValidateException;

    Collection<Project> sortAllByUserId(
            @Nullable final String id,
            @Nullable final String parameter
    ) throws DataValidateException;

    Collection<Project> findAllByPartOfNameOrDescription(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId
    ) throws DataValidateException;
}
