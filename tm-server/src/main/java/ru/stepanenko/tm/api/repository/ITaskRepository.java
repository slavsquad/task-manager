package ru.stepanenko.tm.api.repository;

import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;

import java.sql.Connection;
import java.util.Collection;

public interface ITaskRepository {

    @Select("SELECT * FROM app_task WHERE id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Task findOne(@NotNull final String id);

    @Select("SELECT * FROM app_task")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAll();

    @Delete("DELETE FROM app_task")
    Integer removeAll();

    @Delete("DELETE FROM app_task where id = #{id}")
    Integer remove(
            @NotNull final String id);

    @Insert("INSERT INTO app_task(id, name, description, dateBegin, dateEnd, status, user_id, project_id) VALUES (#{id}, #{name}, #{description}, #{dateBegin}, #{dateEnd}, #{status}, #{userId}, #{projectId})")
    Integer persist(
            @NotNull final Task entity);

    @Update("UPDATE app_task SET name = #{name}, description = #{description}, dateBegin = #{dateBegin}, dateEnd = #{dateEnd}, status = #{status} where id = #{id}")
    Integer merge(
            @NotNull final Task task);

    Connection getConnection();

    @Select("SELECT * FROM app_task WHERE user_id = #{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByUserId(
            @NotNull final String id);

    @Select("SELECT * FROM app_task WHERE project_id = #{id} AND user_id = #{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByProjectAndUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Select("SELECT * FROM app_task WHERE id = #{id} AND user_id = #{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Task findOneByUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Delete("DELETE FROM app_task where id = #{id} AND user_id = #{userId}")
    Integer removeOneByUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Delete("DELETE FROM app_task where user_id = #{id}")
    Integer removeAllByUserId(
            @NotNull final String id);

    @Delete("DELETE FROM app_task where project_id = #{id} AND user_id = #{userId}")
    Integer removeAllByProjectAndUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Select("SELECT * FROM app_task WHERE user_id = #{id} ORDER BY #{parameter} DESC")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> sortAllByUserId(
            @Param("id") @NotNull final String id,
            @Param("parameter") @NotNull final String parameter);

    @Select("SELECT * FROM app_task WHERE user_id = #{userId} and (name LIKE CONCAT(CONCAT('%',#{name}),'%') OR description LIKE CONCAT(CONCAT('%',#{description}),'%'))")
    @Results(value = {
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByPartOfNameOrDescription(
            @Param("name") @NotNull final String name,
            @Param("description") @NotNull final String description,
            @Param("userId") @NotNull final String userId);
}
