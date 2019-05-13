package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.entity.User;

import java.sql.Connection;
import java.util.Collection;

public interface IUserRepository {

    @NotNull
    String SELECT_BY_ID = "SELECT id, login, passwordHash, role FROM app_user WHERE id = #{id}";
    @NotNull
    String SELECT_ALL = "SELECT id, login, passwordHash, role FROM app_user";
    @NotNull
    String DELETE_ALL = "DELETE FROM app_user";
    @NotNull
    String DELETE_BY_ID = "DELETE FROM app_user where id = #{id}";
    @NotNull
    String INSERT = "INSERT INTO app_user(id, login, passwordHash, role) VALUES(#{id}, #{login}, #{password}, #{role})";
    @NotNull
    String UPDATE = "UPDATE app_user SET login = #{login}, passwordHash = #{password}, role = #{role} where id = #{id}";
    @NotNull
    String SELECT_BY_LOGIN = "SELECT id, login, passwordHash, role FROM app_user WHERE login = #{login}";


    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "passwordHash"),
            @Result(property = "role", column = "role")
    })
    User findOne(@NotNull final String id);


    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "passwordHash"),
            @Result(property = "role", column = "role")
    })
    Collection<User> findAll();


    @Delete(DELETE_ALL)
    Integer removeAll();

    @Delete(DELETE_BY_ID)
    Integer remove(@NotNull final String id);

    @Insert(INSERT)
    Integer persist(@NotNull final User user);

    @Update(UPDATE)
    Integer merge(@NotNull final User user);

    Connection getConnection();

    @Select(SELECT_BY_LOGIN)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "passwordHash"),
            @Result(property = "role", column = "role")
    })
    User findByLogin(@NotNull final String login);
}
