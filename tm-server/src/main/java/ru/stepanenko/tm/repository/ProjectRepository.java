package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IProjectRepository;
import ru.stepanenko.tm.entity.Project;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.EnumUtil;
import ru.stepanenko.tm.util.FieldConst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@AllArgsConstructor
public final class ProjectRepository implements IProjectRepository {

    @NotNull
    private final Connection connection;

    @Nullable
    @SneakyThrows
    private Project fetch(@Nullable final ResultSet row) {
        if (row == null) return null;
        @NotNull final Project project = new Project();
        project.setId(row.getString(FieldConst.ID));
        project.setName(row.getString(FieldConst.NAME));
        project.setDescription(row.getString(FieldConst.DESCRIPTION));
        project.setDateBegin(row.getDate(FieldConst.DATA_BEGIN));
        project.setDateEnd(row.getDate(FieldConst.DATA_END));
        project.setStatus(EnumUtil.stringToStatus(row.getString(FieldConst.STATUS)));
        project.setUserID(row.getString(FieldConst.USER_ID));
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project findOne(@NotNull final String id) {
        @NotNull final String query = "SELECT * FROM app_project WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull final Project project =  fetch(resultSet);
        resultSet.close();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> findAll() {
        @NotNull final String query = "SELECT * FROM app_project";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Project> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @Nullable
    @SneakyThrows
    public void removeAll()  {
        @NotNull final String query = "DELETE FROM app_project";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project remove(@NotNull final String id)  {
        @NotNull final String query = "DELETE FROM app_project where id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Project project = findOne(id);
        statement.setString(1, id);
        statement.executeUpdate();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project persist(@NotNull final Project project)  {
        @NotNull final String query = "INSERT into app_project(id, name, description, dateBegin, dateEnd, status, user_id) values (?, ?, ?, ?, ?, ?, ? )";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, project.getId());
        statement.setString(2, project.getName());
        statement.setString(3, project.getDescription());
        statement.setString(4, DateFormatter.format(project.getDateBegin()));
        statement.setString(5, DateFormatter.format(project.getDateEnd()));
        statement.setString(6, project.getStatus().toString());
        statement.setString(7, project.getUserID());
        statement.executeUpdate();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project merge(@NotNull final Project project)  {
        @NotNull final String query = "UPDATE app_project SET name = ?, description = ?, dateBegin = ?, dateEnd = ?, status = ? where id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setString(3, DateFormatter.format(project.getDateBegin()));
        statement.setString(4, DateFormatter.format(project.getDateEnd()));
        statement.setString(5, project.getStatus().toString());
        statement.setString(6, project.getId());
        statement.executeUpdate();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> recovery(@NotNull Collection<Project> collection) {
        for (Project project : collection) {
            persist(project);
        }
        return collection;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> findAllByUserId(@NotNull final String id)  {
        @NotNull final String query = "SELECT * FROM app_project WHERE user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Project> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project findOne(@NotNull String id, @NotNull String userId)  {
        @NotNull final String query = "SELECT * FROM app_project WHERE id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.setString(2, userId);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull Project project = fetch(resultSet);
        resultSet.close();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Project remove(@NotNull String id, @NotNull String userId)  {
        @NotNull final String query = "DELETE FROM app_project where id = ? AND user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Project project = findOne(id, userId);
        statement.setString(1, id);
        statement.setString(2, userId);
        statement.executeUpdate();
        statement.close();
        return project;
    }

    @Override
    @Nullable
    @SneakyThrows
    public void removeAllByUserID(@NotNull final String id)  {
        @NotNull final String query = "DELETE FROM app_project where user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> sortAllByUserId(@NotNull final String id, Comparator<Project> comparator)  {
        List<Project> projects = new ArrayList<>(findAllByUserId(id));
        Collections.sort(projects, comparator);
        return projects;
    }

    @Override
    @Nullable
    @SneakyThrows
    public Collection<Project> findAllByPartOfNameOrDescription(@NotNull String name, @NotNull String description, @NotNull String userId)  {
        List<Project> findProjects = new ArrayList<>();
        for (Project project : findAllByUserId(userId)) {
            if (project.getName().contains(name) || project.getDescription().contains(description)) {
                findProjects.add(project);
            }
        }
        return findProjects;
    }
}
