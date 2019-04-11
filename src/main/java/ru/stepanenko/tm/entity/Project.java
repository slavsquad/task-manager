package ru.stepanenko.tm.entity;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Project extends BaseEntity {

    private UUID uuid;
    public Project(String name, String description) {
        this.name = name;
        this.description = description;
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
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
