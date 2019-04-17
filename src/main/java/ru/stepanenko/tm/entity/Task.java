package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Task extends BaseEntity {
    private String projectID = "";
    private Date dateStart = null;
    private Date dateEnd = null;
    private String userID = "";

    public Task() {
    }

    public Task(String name, String description, String projectID, String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.projectID = projectID;
        this.userID = userID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
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
