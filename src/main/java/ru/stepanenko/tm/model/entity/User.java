package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Role;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class User extends AbstractEntity implements Serializable {

    @Nullable
    private String login = "";

    @Nullable
    private String password = "";

    @Nullable
    private Role role = Role.USER;
}
