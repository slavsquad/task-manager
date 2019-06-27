package ru.stepanenko.tm.model.dto;

import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.util.Date;

public class TaskDTO extends BaseEntityDTO {

    @Nullable
    private String projectId;

    public TaskDTO(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final Date dateBegin,
            @Nullable final Date dateEnd,
            @Nullable final Status status,
            @Nullable final String projectId,
            @Nullable final String userId) {
        this.name = name;
        this.description = description;
        this.dateBegin = dateBegin;
        this.dateEnd = dateEnd;
        this.status = status;
        this.projectId = projectId;
        this.userId = userId;
    }

    @Nullable
    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(@Nullable String projectId) {
        this.projectId = projectId;
    }
}
