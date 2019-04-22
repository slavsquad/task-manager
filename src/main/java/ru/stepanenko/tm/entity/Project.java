package ru.stepanenko.tm.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Project extends AbstractEntity {
    @NotNull
    private Date dateStart;
    @NotNull
    private Date dateEnd;
    @NotNull
    private String userID;

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
                ", startDate=" + DateFormatter.format(dateStart) +
                ", endDate=" + dateEnd +
                ", userID='" + userID + '\'' +
                '}';
    }
}
