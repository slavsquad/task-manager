package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;
import ru.stepanenko.tm.model.dto.ProjectDTO;
import ru.stepanenko.tm.model.dto.UserDTO;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_user")
public class User extends AbstractEntity implements Serializable {

    @Nullable
    @Column
    private String login = "";

    @Nullable
    @Column(unique = true)
    private String password = "";

    @NotNull
    @Column
    @Enumerated(value = EnumType.STRING)
    private Role role = Role.USER;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Project> projects;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Task> tasks;

    public UserDTO getDTO(){
        @NotNull final UserDTO dto = new UserDTO();
        dto.setId(id);
        dto.setLogin(login);
        dto.setPassword(null);
        dto.setRole(role);
        dto.setName(name);
        dto.setDescription(description);
        return dto;
    }
}
