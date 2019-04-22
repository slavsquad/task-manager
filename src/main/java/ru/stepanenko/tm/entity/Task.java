package ru.stepanenko.tm.entity;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Task extends AbstractEntity {
    @NotNull
    private String projectID = "";
    @NotNull
    private Date dateStart = null;
    @NotNull
    private Date dateEnd = null;
    @NotNull
    private String userID = "";

    public Task() {
    }

    public Task(@NotNull final String name, @NotNull final String description, @NotNull final String projectID, @NotNull final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.projectID = projectID;
        this.userID = userID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(@NotNull final String projectID) {
        this.projectID = projectID;
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
        return "Task{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateStart=" + dateStart +
                ", dateEnd=" + dateEnd +
                ", projectID='" + projectID + '\'' +
                ", userID='" + userID + '\'' +
                '}';
    }
}
