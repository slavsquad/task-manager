package ru.stepanenko.tm.entity;

import org.jetbrains.annotations.NotNull;
import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Project extends AbstractEntity {
    @NotNull
    private Date dateStart;
    @NotNull
    private Date dateEnd;
    @NotNull
    private String userID;

    public Project() {
    }

    public Project(@NotNull final String name, @NotNull final String description, @NotNull final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.userID = userID;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(@NotNull final Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(@NotNull final Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(@NotNull final String userID) {
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
