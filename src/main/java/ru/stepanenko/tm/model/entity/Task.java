package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity implements Serializable {

    @Nullable
    private String projectId;

    public Task(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final String projectId,
            @Nullable final String userId) {
        this.name = name;
        this.description = description;
        this.projectId = projectId;
        this.userId = userId;
    }
}
