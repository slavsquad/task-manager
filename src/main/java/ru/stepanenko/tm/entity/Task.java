package ru.stepanenko.tm.entity;

import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Task extends BaseEntity {
    private String projectID;

    public Task(String id, String name, String description, String projectID) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.projectID = projectID;
    }

    public String getProjectID() {
        return projectID;
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return "Task{" +
                "projectID=" + projectID +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate.format(formatter) +
                ", endDate=" + endDate +
                '}';

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Task task = (Task) o;
        return Objects.equals(projectID, task.projectID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), projectID);
    }
}
