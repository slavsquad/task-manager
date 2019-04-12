package ru.stepanenko.tm.entity;

import java.time.format.DateTimeFormatter;

public class Project extends BaseEntity {

    public Project(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate.format(formatter) +
                ", endDate=" + endDate +
                '}';
    }

}
