package ru.stepanenko.tm.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.TaskDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "app_task")
public class Task extends BaseEntity implements Serializable {

    @Nullable
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TaskDTO getDTO() {
        @NotNull final TaskDTO dto = new TaskDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setDescription(description);
        dto.setDateBegin(dateBegin);
        dto.setDateEnd(dateEnd);
        dto.setStatus(status);
        dto.setUserId(user.getId());
        dto.setProjectId(project == null ? null : project.getId());
        return dto;
    }
}
