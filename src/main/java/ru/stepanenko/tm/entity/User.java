package ru.stepanenko.tm.entity;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.enumerate.Role;

public class User extends AbstractEntity {
    @NotNull
    private String login = "";
    @NotNull
    private String password = "";
    @NotNull
    private Role role = Role.USER;

    public User() {
    }

    public User(@NotNull final String login,@NotNull final String password,@NotNull final Role role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(@NotNull final String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(@NotNull final String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(@NotNull final Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
