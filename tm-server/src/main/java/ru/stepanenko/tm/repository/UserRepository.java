package ru.stepanenko.tm.repository;


import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.api.repository.IUserRepository;
import ru.stepanenko.tm.entity.User;
import ru.stepanenko.tm.util.FieldConst;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
public final class UserRepository implements IUserRepository {

    @NotNull
    private final Connection connection;

    @Nullable
    @SneakyThrows
    private User fetch(@Nullable final ResultSet row) {
        if (row == null) return null;
        @NotNull final User user = new User();
        user.setId(row.getString(FieldConst.ID));
        user.setLogin(row.getString(FieldConst.LOGIN));
        user.setRole(row.getString(FieldConst.ROLE));
        user.setPassword(row.getString(FieldConst.PASSWORD));
        return user;
    }

    @Override
    @SneakyThrows
    public User findOne(@NotNull final String id) {
        @NotNull final String query = "SELECT * FROM app_user WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull User user = fetch(resultSet);
        resultSet.close();
        statement.close();
        return user;
    }

    @Override
    @SneakyThrows
    public Collection<User> findAll() {
        @NotNull final String query = "SELECT * FROM app_user";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        @NotNull final List<User> result = new ArrayList<>();
        while (resultSet.next()) result.add(fetch(resultSet));
        resultSet.close();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer removeAll() {
        @NotNull final String query = "DELETE FROM app_user";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer remove(@NotNull final String id) {
        @NotNull final String query = "DELETE FROM app_user WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, id);
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer persist(@NotNull final User user) {
        @NotNull final String query = "INSERT INTO app_user(id, login, passwordHash, role) VALUES(?, ?, ?, ?)";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, user.getId());
        statement.setString(2, user.getLogin());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getRole().toString());
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    @SneakyThrows
    public Integer merge(@NotNull final User user) {
        @NotNull final String query = "UPDATE app_user SET login = ?, passwordHash = ?, role= ? WHERE id = ?";
        @NotNull final PreparedStatement statement = getConnection().prepareStatement(query);
        statement.setString(1, user.getLogin());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getRole().toString());
        statement.setString(4, user.getId());
        @NotNull final Integer result = statement.executeUpdate();
        statement.close();
        return result;
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    @SneakyThrows
    public User findByLogin(@NotNull final String login) {
        @NotNull final String query = "SELECT * FROM app_user WHERE login = ?";
        @NotNull final PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, login);
        @NotNull final ResultSet resultSet = statement.executeQuery();
        if (!resultSet.next()) return null;
        @NotNull final User user = fetch(resultSet);
        resultSet.close();
        statement.close();
        return user;
    }
}
