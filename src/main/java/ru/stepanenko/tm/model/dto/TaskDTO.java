package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO extends BaseEntityDTO implements Serializable {

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

}
