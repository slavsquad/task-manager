package ru.stepanenko.tm.entity;

import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Task extends BaseEntity {
    private UUID projectUUID;

    public Task(String name, String description, UUID projectUUID) {
        this.name = name;
        this.description = description;
        this.projectUUID = projectUUID;

    }

    public UUID getProjectUUID() {
        return projectUUID;
    }

    public void setProjectUUID(UUID projectUUID) {
        this.projectUUID = projectUUID;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy");
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate.format(formatter) +
                ", endDate=" + endDate +
                '}';
    }
}
