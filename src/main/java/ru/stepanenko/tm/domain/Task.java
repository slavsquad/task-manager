package ru.stepanenko.tm.domain;

import java.time.format.DateTimeFormatter;

public class Task extends BaseEntity {
    private int projectID;

    public Task(String name, String description, int projectID) {
        this.name = name;
        this.description = description;
        this.projectID = projectID;

    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime.format(formatter) +
                '}';
    }
}
