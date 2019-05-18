package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.model.dto.TaskDTO;

import javax.persistence.*;
import java.io.Serializable;


@Getter
@Setter
@NoArgsConstructor
public class Task extends BaseEntity implements Serializable {

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id")
    private User user;

    public TaskDTO getDTO(){
        @NotNull final TaskDTO dto = new TaskDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setDateBegin(dateBegin);
        dto.setDateEnd(dateEnd);
        dto.setStatus(status);
        dto.setUserId(user.getId());
        dto.setProjectId(project.getId());
        return dto;
    }
}
