package ru.stepanenko.tm.domain;

import java.time.format.DateTimeFormatter;

public class Project extends BaseEntity {

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", dateTime=" + dateTime.format(formatter) +
                '}';
    }
}
