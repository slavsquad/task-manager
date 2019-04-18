package ru.stepanenko.tm.entity;

import java.util.Date;

public class Task extends AbstractEntity {
    private String projectID = "";
    private Date dateStart = null;
    private Date dateEnd = null;
    private String userID = "";

    public Task() {
    }

    public Task(final String name, final String description, final String projectID, final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.projectID = projectID;
        this.userID = userID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(final String projectID) {
        this.projectID = projectID;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(final Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(final Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(final String userID) {
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
