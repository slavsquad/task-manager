package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Project extends BaseEntity {
    private Date dateStart;
    private Date dateEnd;
    private String userID;

    public Project() {
    }

    public Project(String name, String description, String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.userID = userID;
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
