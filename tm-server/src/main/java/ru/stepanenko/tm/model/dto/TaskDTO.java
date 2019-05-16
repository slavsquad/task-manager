package ru.stepanenko.tm.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class TaskDTO extends BaseEntityDTO implements Serializable {

    @NotNull
    private String projectId;
}
