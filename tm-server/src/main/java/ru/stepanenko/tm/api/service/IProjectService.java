package ru.stepanenko.tm.api.service;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.entity.Project;
import ru.stepanenko.tm.exception.InputDataValidateException;

import java.util.Collection;

public interface IProjectService {

    Project create(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userID)
            throws InputDataValidateException;

    Project edit(
            @NotNull final String id,
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String status,
            @NotNull String userId)
            throws InputDataValidateException;

    Project findOne(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    Project remove(
            @NotNull final String id,
            @NotNull final String userId)
            throws InputDataValidateException;

    void clear()
            throws InputDataValidateException;

    Project findOne(
            @NotNull final String id)
            throws InputDataValidateException;

    Project remove(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<Project> findAll()
            throws InputDataValidateException;

    Collection<Project> findAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException;

    void removeAllByUserId(
            @NotNull final String id)
            throws InputDataValidateException;

    Collection<Project> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String comparator)
            throws InputDataValidateException;

    Collection<Project> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId)
            throws InputDataValidateException;
}
