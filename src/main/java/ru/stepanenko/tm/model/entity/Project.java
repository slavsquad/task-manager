package ru.stepanenko.tm.model.entity;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.model.dto.ProjectDTO;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "app_project")
public class Project extends BaseEntity implements Serializable {

    @Nullable
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks;

    public ProjectDTO getDTO() {
        @NotNull final ProjectDTO dto = new ProjectDTO(
                name,
                description,
                dateBegin,
                dateEnd,
                status,
                user.getId());
        dto.setId(id);
        return dto;
    }

    @Nullable
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(@Nullable List<Task> tasks) {
        this.tasks = tasks;
    }
}
