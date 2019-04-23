package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.stepanenko.tm.enumerate.Status;
import ru.stepanenko.tm.util.DateFormatter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Project extends AbstractEntity implements Serializable {
    @Nullable
    private Date dateStart;
    @Nullable
    private Date dateEnd;
    @NotNull
    private String userID = "";
    @NotNull
    private Status status = Status.PLANNED;

    public Project(@NotNull final String name, @NotNull final String description, @NotNull final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.userID = userID;
    }


    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + DateFormatter.format(dateStart) +
                ", dateEnd=" + DateFormatter.format(dateEnd) +
                ", status=" + status +
                ", userID='" + userID + '\'' +
                '}';
    }
}
