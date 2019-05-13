package ru.stepanenko.tm.api.repository;

import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.User;
import java.sql.Connection;
import java.util.Collection;

public interface IUserRepository {

    @Select("SELECT id, login, passwordHash, role FROM app_user WHERE id = #{id}")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "passwordHash"),
            @Result(property = "role", column = "role")
    })
    User findOne(
            @NotNull final String id);


    @Select("SELECT id, login, passwordHash, role FROM app_user")
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "passwordHash"),
            @Result(property = "role", column = "role")
    })
    Collection<User> findAll();


    @Delete("DELETE FROM app_user")
    Integer removeAll();

    @Delete("DELETE FROM app_user where id = #{id}")
    Integer remove(
            @NotNull final String id);

    @Insert("INSERT INTO app_user(id, login, passwordHash, role) VALUES(#{id}, #{login}, #{password}, #{role})")
    Integer persist(
            @NotNull final User user);

    @Update("UPDATE app_user SET login = #{login}, passwordHash = #{password}, role = #{role} where id = #{id}")
    Integer merge(
            @NotNull final User user);

    Connection getConnection();

    @Select("SELECT id, login, passwordHash, role FROM app_user WHERE login = #{login}")
    @Results(value = {
            @Result(property = "password", column = "passwordHash"),
    })
    User findByLogin(
            @NotNull final String login);
}
