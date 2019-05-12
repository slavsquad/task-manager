package ru.stepanenko.tm.api.repository;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.entity.Project;
import java.sql.Connection;
import java.util.Collection;
import org.apache.ibatis.annotations.*;
import ru.stepanenko.tm.enumerate.Status;

public interface IProjectRepository {

    @NotNull String SELECT_BY_ID = "SELECT * FROM app_project WHERE id = #{id}";
    @NotNull String SELECT_ALL = "SELECT * FROM app_project";
    @NotNull String DELETE_ALL = "DELETE FROM app_project";
    @NotNull String DELETE_BY_ID = "DELETE FROM app_project where id = #{id}";
    @NotNull String INSERT = "INSERT into app_project(id, name, description, dateBegin, dateEnd, status, user_id) VALUES (#{id}, #{name}, #{description}, #{dateBegin}, #{dateEnd}, #{status}, #{userId})";
    @NotNull String UPDATE = "UPDATE app_project SET name = #{name}, description = #{description}, dateBegin = #{dateBegin}, dateEnd = #{dateEnd}, status = #{status} where id = #{id}";
    @NotNull String SELECT_BY_USER_ID = "SELECT * FROM app_project WHERE user_id = #{id}";
    @NotNull String SELECT_BY_ID_AND_USER_ID = "SELECT * FROM app_project WHERE id = #{id} AND user_id = #{userId}";
    @NotNull String DELETE_BY_ID_AND_USER_ID = "DELETE FROM app_project WHERE id = #{id} AND user_id = #{userId}";
    @NotNull String DELETE_ALL_BY_USER_ID = "DELETE FROM app_project WHERE user_id = #{id}";
    @NotNull String SELECT_ALL_BY_USER_ID_AND_ORDER_BY_PARAMETER_DESC = "SELECT * FROM app_project WHERE user_id = #{id} ORDER BY #{parameter} DESC";
    @NotNull String SELECT_ALL_BY_USER_ID_LIKE_NAME_AND_DESCRIPTION = "SELECT * FROM app_project WHERE user_id = #{userId} and (name LIKE #{name} OR description LIKE #{description})";

    @Select(SELECT_BY_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Project findOne(@NotNull final String id);

    @Select(SELECT_ALL)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAll();

    @Delete(DELETE_ALL)
    Integer removeAll();

    @Delete(DELETE_BY_ID)
    Integer remove(@NotNull final String id);

    @Insert(INSERT)
    Integer persist(@NotNull final Project project);

    @Update(UPDATE)
    Integer merge(@NotNull final Project project);

    Connection getConnection();

    @Select(SELECT_BY_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAllByUserId(@NotNull final String id);

    @Select(SELECT_BY_ID_AND_USER_ID)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Project findOneByUserId(@Param("id") @NotNull final String id,
                            @Param("userId") @NotNull final String userId);

    @Delete(DELETE_BY_ID_AND_USER_ID) Integer removeOneByUserId(@Param("id") @NotNull final String id,
                                                                @Param("userId") @NotNull final String userId);

    @Delete(DELETE_ALL_BY_USER_ID)
    Integer removeAllByUserID(@NotNull final String id);

    @Select(SELECT_ALL_BY_USER_ID_AND_ORDER_BY_PARAMETER_DESC)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> sortAllByUserId(@Param("id") @NotNull final String id,
                                        @Param("parameter") @NotNull final String parameter);


    @Select(SELECT_ALL_BY_USER_ID_LIKE_NAME_AND_DESCRIPTION)
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "description", column = "description"),
            @Result(property = "dateBegin", column = "dateBegin"),
            @Result(property = "dateEnd", column = "dateEnd"),
            @Result(property = "status", column = "status"),
            @Result(property = "userId", column = "user_id")
    })
    Collection<Project> findAllByPartOfNameOrDescription(@Param("name") @NotNull final String name,
                                                         @Param("description") @NotNull final String description,
                                                         @Param("userId") @NotNull final String userId);
}
