package ru.stepanenko.tm.api.repository;

import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Session;
import java.sql.Connection;
import java.util.Collection;

public interface ISessionRepository {

    @Select("SELECT * FROM app_session WHERE id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Session findOne(
            @NotNull final String id);

    @Select("SELECT * FROM app_session")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Session> findAll();

    @Delete("DELETE FROM app_session")
    Integer removeAll();

    @Delete("DELETE FROM app_session WHERE id = #{id}")
    Integer remove(
            @NotNull final String id);

    @Insert("INSERT INTO app_session(id, signature, timestamp, user_id) VALUES (#{id}, #{signature}, #{timestamp,}, #{userId})")
    Integer persist(
            @NotNull final Session session);

    @Update("UPDATE app_session SET signature = #{signature}, timestamp = #{timestamp} WHERE id = #{id}")
    Integer merge(
            @NotNull final Session session);

    Connection getConnection();

    @Select("SELECT * FROM app_session WHERE user_id = #{id}")
    @Results(value = {
            @Result(property = "userId", column = "user_id")
    })
    Collection<Session> findAllByUserId(
            @NotNull final String id);
}
