package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Project extends AbstractEntity {
    private Date dateStart;
    private Date dateEnd;
    private String userID;

    public Project() {
    }

    public Project(final String name, final String description, final String userID) {
        this.name = name;
        this.description = description;
        this.dateStart = new Date();
        this.userID = userID;
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
