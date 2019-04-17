package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Project extends BaseEntity {
    private Date startDate;
    private Date endDate;
    private String userID;

    public Project() {
    }

    public Project(String name, String description, String userID) {
        this.name = name;
        this.description = description;
        this.startDate = new Date();
        this.userID = userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + DateFormatter.format(startDate) +
                ", endDate=" + endDate +
                ", userID='" + userID + '\'' +
                '}';
    }
}
