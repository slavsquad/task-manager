package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.model.entity.Task;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@AllArgsConstructor
public final class TaskRepository implements ITaskRepository {

    @NotNull
    private final Connection connection;

    @Nullable
    @SneakyThrows
    private Task fetch(
            @Nullable final ResultSet row) {
        if (row == null) return null;
        @NotNull final Task task = new Task();
        task.setId(row.getString(FieldConst.ID));
        task.setName(row.getString(FieldConst.NAME));
        task.setDescription(row.getString(FieldConst.DESCRIPTION));
        task.setDateBegin(row.getDate(FieldConst.DATA_BEGIN));
        task.setDateEnd(row.getDate(FieldConst.DATA_END));
        task.setStatus(row.getString(FieldConst.STATUS));
        task.setUserId(row.getString(FieldConst.USER_ID));
        task.setProjectId(row.getString(FieldConst.PROJECT_ID));
        return task;
    }

    @Override
    @SneakyThrows
    public Task findOne(
            @NotNull final String id) {
        @NotNull final String query = "SELECT * FROM app_task WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull final Task task = fetch(resultSet);
        resultSet.close();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAll() {
        @NotNull final String query = "SELECT * FROM app_task";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Task> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer removeAll() {
        @NotNull final String query = "DELETE FROM app_task";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer remove(
            @NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_task WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.executeUpdate();
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer persist(
            @NotNull final Task task) {
        @NotNull final String query = "INSERT INTO app_task(id, name, description, dateBegin, dateEnd, status, user_id, project_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, task.getId());
        statement.setString(2, task.getName());
        statement.setString(3, task.getDescription());
        statement.setString(4, DateFormatter.format(task.getDateBegin()));
        statement.setString(5, DateFormatter.format(task.getDateEnd()));
        statement.setString(6, task.getStatus().toString());
        statement.setString(7, task.getUserId());
        statement.setString(8, task.getProjectId());
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer merge(
            @NotNull final Task task) {
        @NotNull final String query = "UPDATE app_task SET name = ?, description = ?, dateBegin = ?, dateEnd = ?, status = ? WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        statement.setString(3, DateFormatter.format(task.getDateBegin()));
        statement.setString(4, DateFormatter.format(task.getDateEnd()));
        statement.setString(5, task.getStatus().toString());
        statement.setString(6, task.getId());
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return connection;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAllByUserId(
            @NotNull final String id) {
        @NotNull final String query = "SELECT * FROM app_task WHERE user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Task> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        return result;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAllByProjectAndUserId(
            @NotNull String id,
            @NotNull String userId) {
        @NotNull final String query = "SELECT * FROM app_task WHERE project_id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Task> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        return result;
    }

    @Override
    @SneakyThrows
    public Task findOneByUserId(
            @NotNull final String id,
            @NotNull final String userId) {
        @NotNull final String query = "SELECT * FROM app_task WHERE id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull Task task = fetch(resultSet);
        resultSet.close();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public Integer removeOneByUserId(
            @NotNull String id,
            @NotNull String userId) {
        @NotNull final String query = "DELETE FROM app_task WHERE id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer removeAllByUserId(
            @NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_task WHERE user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer removeAllByProjectAndUserId(
            @NotNull final String id,
            @NotNull final String userId) {
        @NotNull final String query = "DELETE FROM app_task WHERE project_id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Collection<Task> sortAllByUserId(
            @NotNull final String id,
            @NotNull final String parameter) {
        @NotNull final String query = "SELECT * FROM app_task WHERE user_id = ? ORDER BY ? DESC";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, parameter);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Task> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAllByPartOfNameOrDescription(
            @NotNull final String name,
            @NotNull final String description,
            @NotNull final String userId) {
        @NotNull final String query = "SELECT * FROM app_task WHERE user_id = ? AND (name LIKE ? OR description LIKE ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, userId);
        statement.setString(2, name);
        statement.setString(3, description);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Task> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }
}
