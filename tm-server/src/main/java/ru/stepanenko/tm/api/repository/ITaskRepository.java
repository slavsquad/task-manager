package ru.stepanenko.tm.api.repository;

import lombok.SneakyThrows;
import org.apache.ibatis.annotations.*;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.entity.Task;

import java.sql.Connection;
import java.util.Collection;
import java.util.Comparator;

public interface ITaskRepository {

    @NotNull
    String SELECT_BY_ID = "SELECT * FROM app_task WHERE id = #{id}";
    @NotNull
    String SELECT_ALL = "SELECT * FROM app_task";
    @NotNull
    String DELETE_ALL = "DELETE FROM app_task";
    @NotNull
    String DELETE_BY_ID = "DELETE FROM app_task where id = #{id}";
    @NotNull
    String INSERT = "INSERT INTO app_task(id, name, description, dateBegin, dateEnd, status, user_id, project_id) VALUES (#{id}, #{name}, #{description}, #{dateBegin}, #{dateEnd}, #{status}, #{userId}, #{projectId})";
    @NotNull
    String UPDATE = "UPDATE app_task SET name = #{name}, description = #{description}, dateBegin = #{dateBegin}, dateEnd = #{dateEnd}, status = #{status} where id = ?";
    @NotNull
    String SELECT_ALL_BY_USER_ID = "SELECT * FROM app_task WHERE user_id = #{userId}";
    @NotNull
    String SELECT_ALL_BY_PROJECT_AND_USER_ID = "SELECT * FROM app_task WHERE project_id = #{id} AND user_id = #{userId}";
    @NotNull
    String SELECT_BY_ID_AND_USER_ID = "SELECT * FROM app_task WHERE id = #{id} AND user_id = #{userId}";
    @NotNull
    String DELETE_BY_ID_AND_USER_ID = "DELETE FROM app_task where id = #{id} AND user_id = #{userId}";
    @NotNull
    String DELETE_ALL_BY_USER_ID = "DELETE FROM app_task where user_id = #{id}";
    @NotNull
    String DELETE_ALL_BY_PROJECT_AND_USER_ID = "DELETE FROM app_task where project_id = #{id} AND user_id = #{userId}";
    @NotNull
    String SELECT_ALL_BY_USER_ID_AND_ORDER_BY_PARAMETER_DESC = "SELECT * FROM app_task WHERE user_id = #{id} ORDER BY #{parameter} DESC";
    @NotNull
    String SELECT_ALL_BY_USER_ID_LIKE_NAME_OR_DESCRIPTION = "SELECT * FROM app_task WHERE user_id = #{id} and (name LIKE #{name} OR description LIKE #{description})";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Task findOne(@NotNull final String id);

    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAll();

    @Delete(DELETE_ALL)
    Integer removeAll();

    @Delete(DELETE_BY_ID)
    Integer remove(@NotNull final String id);

    @Insert(INSERT)
    Integer persist(@NotNull final Task entity);

    @Update(UPDATE)
    Integer merge(@NotNull final Task task);

    Connection getConnection();

    @Select(SELECT_ALL_BY_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByUserId(@NotNull final String id);

    @Select(SELECT_ALL_BY_PROJECT_AND_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByProjectAndUserId(@NotNull final String id, @NotNull final String userId);

    @Select(SELECT_BY_ID_AND_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Task findOne(@NotNull final String id, @NotNull final String userId);


    @Delete(DELETE_BY_ID_AND_USER_ID)
    Integer remove(@NotNull final String id, @NotNull final String userId);

    @Delete(DELETE_ALL_BY_USER_ID)
    Integer removeAllByUserId(@NotNull final String id);

    @Delete(DELETE_ALL_BY_PROJECT_AND_USER_ID)
    Integer removeAllByProjectAndUserId(@NotNull final String id, @NotNull final String userId);

    @Select(SELECT_ALL_BY_USER_ID_AND_ORDER_BY_PARAMETER_DESC)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> sortAllByUserId(@NotNull final String id, @NotNull final String parameter);

    @Select(SELECT_ALL_BY_USER_ID_LIKE_NAME_OR_DESCRIPTION)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "projectId", column = "project_id")
    })
    Collection<Task> findAllByPartOfNameOrDescription(@NotNull final String name, @NotNull final String description, @NotNull final String userId);

    default void recovery(@NotNull final Collection<Task> collection) {
        for (Task task : collection) {
            persist(task);
        }
    }
}
