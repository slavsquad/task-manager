package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.util.EnumUtil;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class User extends AbstractEntity implements Serializable {

    @Nullable
    private String login = "";

    @Nullable
    private String password = "";

    @NotNull
    private Role role = Role.USER;

    @Nullable
    private Collection<Project> projects;

    @Nullable
    private Collection<Task> tasks;

    public User(
            @Nullable final String login,
            @Nullable final String password,
            @NotNull final String role,
            @NotNull final Collection<Project> projects) {
        this.login = login;
        this.password = password;
        this.role = EnumUtil.stringToRole(role);
        this.projects = projects;
    }

    public void setRole(
            @NotNull final String role) {
        this.role = EnumUtil.stringToRole(role);
    }

    @NotNull
    public String getRole() {
        return role.toString();
    }
}
