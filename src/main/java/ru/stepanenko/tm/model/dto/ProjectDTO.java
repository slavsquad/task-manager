package ru.stepanenko.tm.model.dto;


import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;

import java.util.Date;

public class ProjectDTO extends BaseEntityDTO {

    public ProjectDTO(
            @Nullable final String name,
            @Nullable final String description,
            @Nullable final Date dateBeing,
            @Nullable final Date dateEnd,
            @Nullable final Status status,
            @Nullable final String userId) {
        this.name = name;
        this.description = description;
        this.dateBegin = dateBeing;
        this.dateEnd = dateEnd;
        this.status = status;
        this.userId = userId;
    }


}
