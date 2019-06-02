package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Project extends BaseEntity implements Serializable {

    public Project(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String userId) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }
}
