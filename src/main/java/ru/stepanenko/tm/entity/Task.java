package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.DateFormatter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Task extends AbstractEntity implements Serializable {
    @NotNull
    private String projectID = "";
    @Nullable
    private Date dateStart = null;
    @Nullable
    private Date dateEnd = null;
    @NotNull
    private String userID = "";
    @NotNull
    private Status status = Status.PLANNED;

    public Task(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.projectID = projectID;
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + DateFormatter.format(dateStart) +
                ", dateEnd=" + DateFormatter.format(dateEnd) +
                ", status=" + status +
                ", projectID='" + projectID + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
