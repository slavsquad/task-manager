package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ITaskRepository;
import ru.stepanenko.tm.entity.Task;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.EnumUtil;
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
    private Task fetch(@Nullable final ResultSet row) {
        if (row == null) return null;
        @NotNull final Task task = new Task();
        task.setId(row.getString(FieldConst.ID));
        task.setName(row.getString(FieldConst.NAME));
        task.setDescription(row.getString(FieldConst.DESCRIPTION));
        task.setDateBegin(row.getDate(FieldConst.DATA_BEGIN));
        task.setDateEnd(row.getDate(FieldConst.DATA_END));
        task.setStatus(EnumUtil.stringToStatus(row.getString(FieldConst.STATUS)));
        task.setUserID(row.getString(FieldConst.USER_ID));
        task.setProjectID(row.getString(FieldConst.PROJECT_ID));
        return task;
    }

    @Override
    @SneakyThrows
    public Task findOne(@NotNull final String id) {
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
    public void removeAll() {
        @NotNull final String query = "DELETE FROM app_task";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @SneakyThrows
    public Task remove(@NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_task where id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Task task = findOne(id);
        statement.setString(1, id);
        statement.executeUpdate();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public Task persist(@NotNull final Task task) {
        @NotNull final String query = "INSERT into app_task(id, name, description, dateBegin, dateEnd, status, user_id, project_id) values (?, ?, ?, ?, ?, ?, ?, ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, task.getId());
        statement.setString(2, task.getName());
        statement.setString(3, task.getDescription());
        statement.setString(4, DateFormatter.format(task.getDateBegin()));
        statement.setString(5, DateFormatter.format(task.getDateEnd()));
        statement.setString(6, task.getStatus().toString());
        statement.setString(7, task.getUserID());
        statement.setString(8, task.getProjectID());
        statement.executeUpdate();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public Task merge(@NotNull final Task task) {
        @NotNull final String query = "UPDATE app_task SET name = ?, description = ?, dateBegin = ?, dateEnd = ?, status = ? where id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, task.getName());
        statement.setString(2, task.getDescription());
        statement.setString(3, DateFormatter.format(task.getDateBegin()));
        statement.setString(4, DateFormatter.format(task.getDateEnd()));
        statement.setString(5, task.getStatus().toString());
        statement.setString(6, task.getId());
        statement.executeUpdate();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public Collection<Task> recovery(@NotNull Collection<Task> collection) {
        for (Task task : collection) {
            persist(task);
        }
        return collection;
    }

    @Override
    @SneakyThrows
    public Connection getConnection() {
        return connection;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAllByUserId(@NotNull final String id) {
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
    public Collection<Task> findAllByProjectId(@NotNull String id, @NotNull String userId) {
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
    public Task findOne(@NotNull final String id, @NotNull final String userId) {
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
    public Task remove(@NotNull String id, @NotNull String userId) {
        @NotNull final String query = "DELETE FROM app_task where id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Task task = findOne(id, userId);
        statement.setString(1, id);
        statement.setString(2, userId);
        statement.executeUpdate();
        statement.close();
        return task;
    }

    @Override
    @SneakyThrows
    public void removeAllByUserId(@NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_task where user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @SneakyThrows
    public void removeAllByProjectId(@NotNull final String id, @NotNull final String userId) {
        @NotNull final String query = "DELETE FROM app_task where project_id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @SneakyThrows
    public Collection<Task> sortAllByUserId(@NotNull String id, Comparator<Task> comparator) {
        List<Task> tasks = new ArrayList<>(findAllByUserId(id));
        Collections.sort(tasks, comparator);
        return tasks;
    }

    @Override
    @SneakyThrows
    public Collection<Task> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId) {
        List<Task> findTasks = new ArrayList<>();
        for (Task task : findAllByUserId(userId)) {
            if (task.getName().contains(name) || task.getDescription().contains(description)) {
                findTasks.add(task);
            }
        }
        return findTasks;
    }
}
