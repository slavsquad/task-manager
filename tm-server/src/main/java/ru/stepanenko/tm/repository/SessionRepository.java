package ru.stepanenko.tm.repository;

import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.ISessionRepository;
import ru.stepanenko.tm.entity.Session;
import ru.stepanenko.tm.util.DateFormatter;
import ru.stepanenko.tm.util.FieldConst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public final class SessionRepository implements ISessionRepository {

    @NotNull
    private final Connection connection;

    @Nullable
    @SneakyThrows
    private Session fetch(
            @Nullable final ResultSet row) {
        if (row == null) return null;
        @NotNull final Session session = new Session();
        session.setId(row.getString(FieldConst.ID));
        session.setSignature(row.getString(FieldConst.SIGNATURE));
        session.setTimestamp(row.getDate(FieldConst.TIMESTAMP));
        session.setUserId(row.getString(FieldConst.USER_ID));
        return session;
    }

    @Override
    @SneakyThrows
    public Session findOne(
            @NotNull final String id) {
        @NotNull final String query = "SELECT * FROM app_session WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull final Session session = fetch(resultSet);
        resultSet.close();
        statement.close();
        return session;
    }

    @Override
    @SneakyThrows
    public Collection<Session> findAll() {
        @NotNull final String query = "SELECT * FROM app_session";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Session> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer removeAll() {
        @NotNull final String query = "DELETE FROM app_session";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer remove(
            @NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_session WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer persist(
            @NotNull final Session session) {
        @NotNull final String query = "INSERT INTO app_session(id, signature, timestamp, user_id) VALUES (?, ?, ?, ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, session.getId());
        statement.setString(2, session.getSignature());
        statement.setString(3, DateFormatter.format(session.getTimestamp()));
        statement.setString(4, session.getUserId());
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer merge(
            @NotNull final Session session) {
        @NotNull final String query = "UPDATE app_session SET signature = ?, timestamp = ? WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, session.getSignature());
        statement.setString(2, DateFormatter.format(session.getTimestamp()));
        statement.setString(3, session.getId());
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
    public Collection<Session> findAllByUserId(
            @NotNull String id) {
        @NotNull final String query = "SELECT * FROM app_session WHERE user_id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<Session> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        return result;
    }
}
