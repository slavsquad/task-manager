package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Session;

import java.sql.Connection;
import java.util.Collection;

public interface ISessionRepository {

    @NotNull
    String SELECT_BY_ID = "SELECT * FROM app_session WHERE id = #{id}";
    @NotNull
    String SELECT_ALL = "SELECT * FROM app_session";
    @NotNull
    String DELETE_ALL = "DELETE FROM app_session";
    @NotNull
    String DELETE_BY_ID = "DELETE FROM app_session WHERE id = #{id}";
    @NotNull
    String UPDATE = "UPDATE app_session SET signature = #{signature}, timestamp = #{timestamp} WHERE id = #{id}";
    @NotNull
    String INSERT = "INSERT INTO app_session(id, signature, timestamp, user_id) VALUES (#{id}, #{signature}, #{timestamp,}, #{userId})";
    @NotNull
    String SELECT_ALL_BY_USER_ID = "SELECT * FROM app_session WHERE user_id = #{id}";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "signature", column = "signature"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "userId", column = "user_id")
    })
    Session findOne(@NotNull final String id);

    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "signature", column = "signature"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Session> findAll();

    @Delete(DELETE_ALL)
    Integer removeAll();

    @Delete(DELETE_BY_ID)
    Integer remove(@NotNull final String id);

    @Insert(INSERT)
    Integer persist(@NotNull final Session session);

    @Update(UPDATE)
    Integer merge(@NotNull final Session session);

    Connection getConnection();

    @Select(SELECT_ALL_BY_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "signature", column = "signature"),
            @Result(property = "timestamp", column = "timestamp"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Session> findAllByUserId(@NotNull final String id);
}
