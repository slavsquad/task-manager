package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;

import java.sql.Connection;
import java.util.Collection;

import org.apache.ibatis.annotations.*;

public interface IProjectRepository {

    @Select("SELECT * FROM app_project WHERE id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Project findOne(
            @NotNull final String id);

    @Select("SELECT * FROM app_project")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAll();

    @Delete("DELETE FROM app_project")
    Integer removeAll();

    @Delete("DELETE FROM app_project where id = #{id}")
    Integer remove(
            @NotNull final String id);

    @Insert("INSERT into app_project(id, name, description, dateBegin, dateEnd, status, user_id) VALUES (#{id}, #{name}, #{description}, #{dateBegin}, #{dateEnd}, #{status}, #{userId})")
    Integer persist(
            @NotNull final Project project);

    @Update("UPDATE app_project SET name = #{name}, description = #{description}, dateBegin = #{dateBegin}, dateEnd = #{dateEnd}, status = #{status} where id = #{id}")
    Integer merge(
            @NotNull final Project project);

    Connection getConnection();

    @Select("SELECT * FROM app_project WHERE user_id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAllByUserId(
            @NotNull final String id);

    @Select("SELECT * FROM app_project WHERE id = #{id} AND user_id = #{userId}")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Project findOneByUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Delete("DELETE FROM app_project WHERE id = #{id} AND user_id = #{userId}")
    Integer removeOneByUserId(
            @Param("id") @NotNull final String id,
            @Param("userId") @NotNull final String userId);

    @Delete("DELETE FROM app_project WHERE user_id = #{id}")
    Integer removeAllByUserID(
            @NotNull final String id);

    @Select("SELECT * FROM app_project WHERE user_id = #{id} ORDER BY #{parameter} DESC")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> sortAllByUserId(
            @Param("id") @NotNull final String id,
            @Param("parameter") @NotNull final String parameter);


    @Select("SELECT * FROM app_project WHERE user_id = #{userId} and (name LIKE CONCAT(CONCAT('%',#{name}),'%') OR description LIKE CONCAT(CONCAT('%',#{description}),'%'))")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAllByPartOfNameOrDescription(
            @Param("name") @NotNull final String name,
            @Param("description") @NotNull final String description,
            @Param("userId") @NotNull final String userId);
}
