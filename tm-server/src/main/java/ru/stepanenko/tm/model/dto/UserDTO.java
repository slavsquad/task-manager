package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO extends AbstractEntityDTO implements Serializable {

    @Nullable
    private String login = "";

    @Nullable
    private String password = "";

    @NotNull
    private Role role = Role.USER;
}
