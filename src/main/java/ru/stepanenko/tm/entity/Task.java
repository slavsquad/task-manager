package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

import java.util.Date;

public class Task extends BaseEntity {
    private String projectID;
    private Date startDate;
    private Date endDate;

    public Task() {
    }

    public Task(String name, String description, String projectID) {
        this.name = name;
        this.description = description;
        this.startDate = new Date();
        this.projectID = projectID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
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

    @Override
    public String toString() {
        return "Task{" +
                "projectID=" + projectID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + DateFormatter.format(startDate) +
                ", endDate=" + endDate +
                '}';
    }
}
