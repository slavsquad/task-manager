package ru.stepanenko.tm.model.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.TaskDTO;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
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
        @NotNull final TaskDTO dto = new TaskDTO(
                name,
                description,
                dateBegin,
                dateEnd,
                status,
                user.getId(),
                project == null ? null : project.getId());
        dto.setId(id);
        return dto;
    }

    @Nullable
    public Project getProject() {
        return project;
    }

    public void setProject(@Nullable Project project) {
        this.project = project;
    }

    @Override
    @Nullable
    public User getUser() {
        return user;
    }

    @Override
    public void setUser(@Nullable User user) {
        this.user = user;
    }
}
