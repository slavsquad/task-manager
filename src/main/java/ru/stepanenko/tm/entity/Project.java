package ru.stepanenko.tm.entity;

import ru.stepanenko.tm.util.DateFormatter;

public class Project extends BaseEntity {

    public Project() {
    }

    public Project(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;

    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + DateFormatter.format(startDate) +
                ", endDate=" + endDate +
                '}';
    }

}
